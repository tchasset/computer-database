package com.excilys.tchasset.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

	private final ComputerService computerService;
	private final CompanyService companyService;

	@Autowired
	public EditComputer(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@GetMapping
	public ModelAndView editComputer(@RequestParam (name = "id", required = true) String ID) {
		ModelAndView view = new ModelAndView("editComputer");

		int id = Integer.parseInt(ID);
		Optional<Computer> computer = computerService.getById(id);

		if(!computer.isPresent()) {
			view.setViewName("redirect:dashboard");
		}

		ComputerDTO computerDTO = ComputerMapper.toDTO(computer.get());
		view.addObject("computer", computerDTO);

		view.addObject("companies", companyService.getCompanies());

		return view;
	}

	@PostMapping
	@ResponseBody
	public ModelAndView editComputer(ComputerDTO computerDTO) {
		ModelAndView view = new ModelAndView("editComputer");

		CompanyDTO companyDTO = computerDTO.getCompanyDTO();
		if (companyDTO.getId() != null){
			Optional<Company> company = companyService.getById(Integer.parseInt(companyDTO.getId()));
			companyDTO = company.map(value -> CompanyMapper.toDTO(value).get()).orElse(null);
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
