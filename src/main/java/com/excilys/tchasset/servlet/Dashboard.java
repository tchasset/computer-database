package com.excilys.tchasset.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.service.ComputerService;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int currentPage=1;
	private static int sizePage=10;
	private static String orderCompany="", orderComputer="";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ComputerService computerService = ComputerService.getInstance();
		int nb=0, nbPages=1;
		
		orderCompany  = request.getParameter("companyName");
		orderComputer = request.getParameter("computerName");
		
		List<Computer> computerList = new ArrayList<Computer>();
		
		//Gère le nombre de pc afficher sur la page
		if(request.getParameter("size")!=null)
			sizePage = Integer.valueOf(request.getParameter("size"));
		
		//Gère le changement de page (affiche la suite des pc)
		if(request.getParameter("page")!=null)
			currentPage = Integer.valueOf(request.getParameter("page"));
			
		request.setAttribute("currentPage", currentPage);
		
		String search = request.getParameter("search")==null ? "":request.getParameter("search");
		
		if(!search.isEmpty()) {
			Optional<Computer> computer = computerService.getByName(search);
			List<Computer> computers	= computerService.getByCompany(search);

			if(computer.isPresent())
				computerList.add(computer.get());
			else if(!computers.isEmpty())
				computerList.addAll(computers);
			nb = computerList.size();
		}
		else {
			nb = computerService.getNbComputers();
			if(orderComputer!=null && (orderComputer.equals("ASC") || orderComputer.equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByComputer(orderComputer));
			else if(orderCompany!=null && (orderCompany.equals("ASC") || orderCompany.equals("DESC")))
				computerList.addAll(computerService.getComputersOrderByCompany(orderCompany));
			else
				computerList.addAll(computerService.getComputersPaginate((currentPage-1)*sizePage, sizePage));
		}
		
		nbPages = (int) Math.ceil((double) nb/(double) sizePage);	
		request.setAttribute("maxPage", nbPages);				
		request.setAttribute("nbComputer", nb);
		request.setAttribute("computerList", computerList);
			
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		ComputerService computerService = ComputerService.getInstance();	
		
		String[] computersId = request.getParameter("selection").split(",");
		
		for(String s : computersId)
			computerService.deleteComputer(Integer.parseInt(s));
		
		doGet(request, response);
	}
}
