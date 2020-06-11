package com.excilys.tchasset.servlet;

import java.io.IOException;
import java.util.List;

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

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ComputerService computerService = ComputerService.getInstance();
		int nb = computerService.getNbComputers(),
			nbPages=1;
		
		request.setAttribute("nbComputer", nb);
		
		List<Computer> computerList;
		
		//Gère le nombre de pc afficher sur la page
		if(request.getParameter("size")!=null)
			sizePage = Integer.valueOf(request.getParameter("size"));
		
		//Gère le changement de page (affiche la suite des pc)
		if(request.getParameter("page")!=null)
			currentPage = Integer.valueOf(request.getParameter("page"));
			
		request.setAttribute("currentPage", currentPage);
		nbPages = (int) Math.ceil((double) nb/(double) sizePage);
		
		request.setAttribute("maxPage", nbPages);
		computerList = computerService.getComputersPaginate((currentPage-1)*sizePage, sizePage);
		request.setAttribute("computerList", computerList);
			
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
