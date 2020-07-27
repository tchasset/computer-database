package com.excilys.tchasset.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	private ComputerService computerService;
	private CompanyService companyService;

	@Autowired
	public AddComputer(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@GetMapping
	public ModelAndView addComputer() {
		ModelAndView view = new ModelAndView("addComputer");
		
		view.addObject("companyName", companyService.getCompanies());
		
		return view;
	}

	@PostMapping
	public ModelAndView addComputer(ComputerDTO computerDTO, CompanyDTO companyDTO) {
		ModelAndView view = new ModelAndView();
		
		if (companyDTO.getId() != null){
			Optional<Company> company = companyService.getById(Integer.valueOf(companyDTO.getId()));
			companyDTO = company.isPresent() ? CompanyMapper.toDTO(company.get()).get() : null;
			computerDTO.setCompanyDTO(companyDTO);
        }
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			Computer computer = ComputerMapper.fromDTO(computerDTO);
			
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
