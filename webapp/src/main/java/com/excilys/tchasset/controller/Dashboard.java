package com.excilys.tchasset.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.tchasset.dto.PaginationDTO;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.ComputerService;


@Controller
@RequestMapping("/dashboard")
public class Dashboard {

	private ComputerService computerService;
	private Page page;
	
	@Autowired
	public Dashboard(ComputerService computerService, Page page) {
		this.computerService = computerService;
		this.page = page;
	}

	@GetMapping
	public ModelAndView dashboard(PaginationDTO paginationDTO) {

		ModelAndView view = new ModelAndView("dashboard");
		
		page.setSize(Integer.valueOf(paginationDTO.getSize()));
		page.setCurrentPage(Integer.valueOf(paginationDTO.getCurrentPage()));
		
		view.addObject("addSuccess", paginationDTO.getAddSuccess());
		view.addObject("editSuccess", paginationDTO.getEditSuccess());
		view.addObject("orderByName", paginationDTO.getOrderByName());
		view.addObject("orderByCompany", paginationDTO.getOrderByCompany());
		view.addObject("size", page.getSize());
		view.addObject("currentPage", page.getCurrentPage());
		view.addObject("search", paginationDTO.getSearch());
		
		List<Computer> computerList = new ArrayList<>();
		int nb=0;
		
		if(!paginationDTO.getSearch().isEmpty()) {
			nb = computerService.getNbBySearch(paginationDTO.getSearch());
			
			computerList.addAll(computerService.getByAllName(page,paginationDTO.getSearch()));
		}
		else {
			nb = computerService.getNbComputers();
			if(paginationDTO.getOrderByName()!=null && (paginationDTO.getOrderByName().equals("ASC") || paginationDTO.getOrderByName().equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByComputer(page, paginationDTO.getOrderByName()));
			else if(paginationDTO.getOrderByCompany()!=null && (paginationDTO.getOrderByCompany().equals("ASC") || paginationDTO.getOrderByCompany().equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByCompany(page, paginationDTO.getOrderByCompany()));
			else
				computerList.addAll(computerService.getAllComputers(page));
		}
		
		page.setNbPages(nb);
		view.addObject("maxPage", page.getNbPages());
		view.addObject("nbComputer", nb);
		view.addObject("computerList", computerList);
		
		return view;
	}
	
	@PostMapping
	public ModelAndView dashboard(@RequestParam (name="selection", required = false) String select) {
		ModelAndView view = new ModelAndView("redirect:dashboard");
		String[] computersId = select.split(",");
		
		for(String s : computersId)
			computerService.deleteComputer(Integer.parseInt(s));
		return view;
	}
}
