package com.excilys.tchasset.servlet;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.ComputerService;


@Controller
@RequestMapping("/dashboard")
public class Dashboard {

	@Autowired
	private Page page;
	@Autowired
	private ComputerService computerService;
	
	@GetMapping
	public ModelAndView dashboard(	@RequestParam (name="addSuccess", required = false) String success,
									@RequestParam (name="editSuccess", required = false) String edit,
									@RequestParam (name="orderByName", required = false) String orderComputer,
									@RequestParam (name="orderByCompany", required = false) String orderCompany,
									@RequestParam (name="size", required = false, defaultValue = "10") String size,
									@RequestParam (name="page", required = false, defaultValue = "1") String currentPage,
									@RequestParam (name="search", required = false, defaultValue = "") String search) {

		ModelAndView view = new ModelAndView("dashboard");
		
		view.addObject("addSuccess", success);
		view.addObject("editSuccess", edit);
		view.addObject("orderByName", orderComputer);
		view.addObject("orderByCompany", orderCompany);
		
		List<Computer> computerList = new ArrayList<>();
		int nb=0;
		
		page.setSizePage(Integer.valueOf(size));
		view.addObject("size", page.getSizePage());
		page.setCurrentPage(Integer.valueOf(currentPage));
		view.addObject("currentPage", page.getCurrentPage());
		
		view.addObject("search", search);
		
		if(!search.isEmpty()) {
			nb = computerService.getByAllName(null,search).size();
			
			computerList.addAll(computerService.getByAllName(page,search));
		}
		else {
			nb = computerService.getNbComputers();
			if(orderComputer!=null && (orderComputer.equals("ASC") || orderComputer.equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByComputer(page, orderComputer));
			else if(orderCompany!=null && (orderCompany.equals("ASC") || orderCompany.equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByCompany(page, orderCompany));
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
