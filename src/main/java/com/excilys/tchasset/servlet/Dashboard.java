package com.excilys.tchasset.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.ComputerService;
import com.excilys.tchasset.spring.SpringConfig;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Page page = new Page();
	private static ComputerService computerService = SpringConfig.getContext().getBean(ComputerService.class);

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		int nb=0;

		String orderComputer = request.getParameter("orderByName");
		String orderCompany  = request.getParameter("orderByCompany");
		request.setAttribute("orderByName", orderComputer);
		request.setAttribute("orderByCompany", orderCompany);
		
		List<Computer> computerList = new ArrayList<Computer>();
		
		//Gère le nombre de pc afficher sur la page
		if(request.getParameter("size")!=null)
			page.setSizePage(Integer.valueOf(request.getParameter("size")));
		
		//Gère le changement de page (affiche la suite des pc)
		if(request.getParameter("page")!=null)
			page.setCurrentPage(Integer.valueOf(request.getParameter("page")));
			
		request.setAttribute("currentPage", page.getCurrentPage());
		
		String search = request.getParameter("search")==null ? "":request.getParameter("search");
		request.setAttribute("search", search);
		
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
		request.setAttribute("maxPage", page.getNbPages());				
		request.setAttribute("nbComputer", nb);
		request.setAttribute("computerList", computerList);
			
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		String[] computersId = request.getParameter("selection").split(",");
		
		for(String s : computersId)
			computerService.deleteComputer(Integer.parseInt(s));
		
		doGet(request, response);
	}
}
