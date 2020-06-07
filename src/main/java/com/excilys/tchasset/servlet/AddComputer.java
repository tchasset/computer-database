package com.excilys.tchasset.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.service.CompanyService;
import com.excilys.tchasset.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		CompanyService companyService = CompanyService.getInstance();
		List<Company> companies = companyService.getCompanies();
		
		request.setAttribute("companyName", companies);
			
		getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request,response);
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ComputerMapper computerMapper   = ComputerMapper.getInstance();
		ComputerService computerService = ComputerService.getInstance();
		Computer computer;
		
		String name 		= request.getParameter("computerName");
		String introduced 	= request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId	= request.getParameter("companyId");
		
		if(name.isEmpty())
			getServletContext().getRequestDispatcher("/views/500.html").forward(request, response); 
		
		else {
			ComputerDTO computerDTO = new ComputerDTO.Builder()	.setName(name)
															   	.setIntroduced(introduced)
																.setDiscontinued(discontinued)
																.setCompanyId(companyId).build();
			
			computer = computerMapper.fromDTO(computerDTO);
			
			if(computer.getDiscontinued()!=null && computer.getIntroduced()!=null)
				if(computer.getDiscontinued().isBefore(computer.getIntroduced()))
					getServletContext().getRequestDispatcher("/views/500.html").forward(request, response);
			
			if(computer.getIntroduced()!=null && computer.getIntroduced().isBefore(LocalDate.of(1970,1,1)) )
				getServletContext().getRequestDispatcher("/views/500.html").forward(request, response);
			
			if(computer.getDiscontinued()!=null && computer.getDiscontinued().isBefore(LocalDate.of(1970,1,1)))
				getServletContext().getRequestDispatcher("/views/500.html").forward(request, response);
			
			computerService.addComputer(computer);
		}
	}
}
