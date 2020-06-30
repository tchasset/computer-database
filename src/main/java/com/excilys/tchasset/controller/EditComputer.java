package com.excilys.tchasset.controller;

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
@RequestMapping("/editComputer")
public class EditComputer {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private static int id;
	
	@GetMapping
	public ModelAndView editComputer(@RequestParam (name = "id", required = true) String ID) {
		ModelAndView view = new ModelAndView("editComputer");
		
		id = Integer.valueOf(ID);
		Optional<Computer> computer = computerService.getById(id);
		if(!computer.isPresent()) {
			view.setViewName("redirect:dashboard");
		}
			
		ComputerDTO computerDTO = ComputerMapper.toDTO(computer.get());
		view.addObject("computer", computerDTO);
			
		view.addObject("companyName", companyService.getCompanies());
		
		return view;
	}

	@PostMapping
	public ModelAndView editComputer(/*@RequestParam (name = "id", required = true) String ID,*/
									@RequestParam (name = "computerName", required = true) String computerName,
									@RequestParam (name = "introduced", required = false) String intro,
									@RequestParam (name = "discontinued", required = false) String disco,
									@RequestParam (name = "companyId", required = false) String compId) {
		ModelAndView view = new ModelAndView("editComputer");
		
		Computer computer;
		
		String name 		= computerName;
		String introduced 	= intro;
		String discontinued = disco;
		String companyId	= compId;

		Optional<Company> company = companyService.getById(Integer.valueOf(companyId));
		CompanyDTO companyDTO = company.isPresent() ? CompanyMapper.toDTO(company.get()) : null;
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId(String.valueOf(id))
															.setName(name)
														   	.setIntroduced(introduced)
															.setDiscontinued(discontinued)
															.setCompanyDTO(companyDTO).build();
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			computer = ComputerMapper.fromDTO(computerDTO);
			
			computerService.updateComputer(computer);
			view.setViewName("redirect:dashboard?editSuccess=1");
		}
		else {
			view.setViewName("redirect:editComputer");
			view.addObject("id",id);
			view.addObject("error", ComputerValidation.messageError);	
		}
		return view;
	}
}
