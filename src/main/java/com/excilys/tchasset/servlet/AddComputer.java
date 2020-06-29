package com.excilys.tchasset.servlet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.CompanyMapper;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.service.CompanyService;
import com.excilys.tchasset.service.ComputerService;
import com.excilys.tchasset.validator.ComputerValidation;

@Controller
@RequestMapping("/addComputer")
public class AddComputer {

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	@GetMapping
	public ModelAndView addComputer() {
		ModelAndView view = new ModelAndView("addComputer");
		
		view.addObject("companyName", companyService.getCompanies());
		
		return view;
	}

	@PostMapping
	public ModelAndView addComputer(@RequestParam (name = "computerName", required = true) String computerName,
									@RequestParam (name = "introduced", required = false) String intro,
									@RequestParam (name = "discontinued", required = false) String disco,
									@RequestParam (name = "companyId", required = false) String compId) {
		ModelAndView view = new ModelAndView();
		
		Computer computer;
		
		String name 		= computerName;
		String introduced 	= intro;
		String discontinued = disco;
		String companyId	= compId;

		Optional<Company> company = companyService.getById(Integer.valueOf(companyId));
		CompanyDTO companyDTO = company.isPresent() ? companyMapper.toDTO(company.get()) : null;
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("0")
															.setName(name)
														   	.setIntroduced(introduced)
															.setDiscontinued(discontinued)
															.setCompanyDTO(companyDTO).build();
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			computer = computerMapper.fromDTO(computerDTO);
			
			computerService.addComputer(computer);
			view.setViewName("redirect:dashboard?addSuccess=1");
		}
		else {
			view.addObject("error", ComputerValidation.messageError);	
			view.setViewName("redirect:addComputer");
		}
		return view;
	}
}
