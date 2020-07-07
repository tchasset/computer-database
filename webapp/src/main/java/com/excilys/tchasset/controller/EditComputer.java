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

	private ComputerService computerService;
	private CompanyService companyService;
	//private static int id;
	
	@Autowired
	public EditComputer(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}
	
	@GetMapping
	public ModelAndView editComputer(@RequestParam (name = "id", required = true) String ID) {
		ModelAndView view = new ModelAndView("editComputer");
		
		int id = Integer.valueOf(ID);
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
	public ModelAndView editComputer(ComputerDTO computerDTO, CompanyDTO companyDTO) {
		ModelAndView view = new ModelAndView("editComputer");
		
		if (companyDTO.getCompanyId() != null){
			Optional<Company> company = companyService.getById(Integer.valueOf(companyDTO.getCompanyId()));
			companyDTO = company.isPresent() ? CompanyMapper.toDTO(company.get()).get() : null;
			computerDTO.setCompanyDTO(companyDTO);
        }
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			Computer computer = ComputerMapper.fromDTO(computerDTO);
			
			computerService.updateComputer(computer);
			view.setViewName("redirect:dashboard?editSuccess=1");
		}
		else {
			view.setViewName("redirect:editComputer");
			view.addObject("id",computerDTO.getId());
			view.addObject("error", ComputerValidation.messageError);	
		}
		return view;
	}
}
