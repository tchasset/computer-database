package com.excilys.tchasset.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.CompanyMapper;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.service.CompanyService;
import com.excilys.tchasset.service.ComputerService;

@WebServlet("/editComputer")
public class EditComputer extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static int id;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		CompanyService companyService = CompanyService.getInstance();
		ComputerService computerService = ComputerService.getInstance();
		
		//Verifie qu'un ordinateur a bien été selectionné
		if(request.getParameter("id")==null)
			getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
			
		//Verifie que l'ordinateur possedant cet id existe
		id = Integer.valueOf(request.getParameter("id"));
		Optional<Computer> computer = computerService.getById(id);
		
		if(!computer.isPresent())
			getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
		
		ComputerDTO computerDTO = ComputerMapper.getInstance().toDTO(computer.get());
		request.setAttribute("computer", computerDTO);
		
		List<Company> companies = companyService.getCompanies();
		request.setAttribute("companyName", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,response);
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ComputerMapper computerMapper   = ComputerMapper.getInstance();
		ComputerService computerService = ComputerService.getInstance();
		Computer computer;
		
		String computerId	= String.valueOf(id);
		String name 		= request.getParameter("computerName");
		String introduced 	= request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId	= request.getParameter("companyId");
		
		if(name.isEmpty())
			getServletContext().getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response); 
		
		else {
			Optional<Company> company = CompanyService.getInstance().getById(Integer.valueOf(companyId));
			CompanyDTO companyDTO = company.isPresent() ? CompanyMapper.getInstance().toDTO(company.get()) : null;
			ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId(computerId)
																.setName(name)
															   	.setIntroduced(introduced)
																.setDiscontinued(discontinued)
																.setCompanyDTO(companyDTO).build();
			
			computer = computerMapper.fromDTO(computerDTO);
			
			if(computer.getDiscontinued()!=null && computer.getIntroduced()!=null)
				if(computer.getDiscontinued().isBefore(computer.getIntroduced()))
					getServletContext().getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
			
			if(computer.getIntroduced()!=null && computer.getIntroduced().isBefore(LocalDate.of(1970,1,1)) )
				getServletContext().getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
			
			if(computer.getDiscontinued()!=null && computer.getDiscontinued().isBefore(LocalDate.of(1970,1,1)))
				getServletContext().getRequestDispatcher("/WEB-INF/views/500.html").forward(request, response);
			
			computerService.updateComputer(computer);
			response.sendRedirect("dashboard");
		}
	}
}
