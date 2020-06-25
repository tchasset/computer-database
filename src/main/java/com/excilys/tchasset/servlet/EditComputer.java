package com.excilys.tchasset.servlet;

import java.io.IOException;
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
import com.excilys.tchasset.spring.SpringConfig;
import com.excilys.tchasset.validator.ComputerValidation;

@WebServlet("/editComputer")
public class EditComputer extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static CompanyMapper companyMapper = SpringConfig.getContext().getBean(CompanyMapper.class);
	private static ComputerMapper computerMapper = SpringConfig.getContext().getBean(ComputerMapper.class);
	private static ComputerService computerService = SpringConfig.getContext().getBean(ComputerService.class);
	private static CompanyService companyService = SpringConfig.getContext().getBean(CompanyService.class);

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		//Verifie qu'un ordinateur a bien été selectionné
		if(request.getParameter("id")==null)
			getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
			
		//Verifie que l'ordinateur possedant cet id existe
		int id = Integer.valueOf(request.getParameter("id"));
		Optional<Computer> computer = computerService.getById(id);

		if(!computer.isPresent())
			getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
		
		ComputerDTO computerDTO = computerMapper.toDTO(computer.get());
		request.setAttribute("computer", computerDTO);
		
		List<Company> companies = companyService.getCompanies();
		request.setAttribute("companyName", companies);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,response);
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		Computer computer;
		
		String computerId	= request.getParameter("id");
		String name 		= request.getParameter("computerName");
		String introduced 	= request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId	= request.getParameter("companyId");
		
		Optional<Company> company = companyService.getById(Integer.valueOf(companyId));
		CompanyDTO companyDTO = company.isPresent() ? companyMapper.toDTO(company.get()) : null;
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId(computerId)
															.setName(name)
														   	.setIntroduced(introduced)
															.setDiscontinued(discontinued)
															.setCompanyDTO(companyDTO).build();
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			computer = computerMapper.fromDTO(computerDTO);
			
			computerService.updateComputer(computer);
			response.sendRedirect("dashboard?editSuccess=1");
		}
		else {
			request.setAttribute("error", ComputerValidation.messageError);
			request.setAttribute("id", computerId);
			doGet(request, response);
		}
	}
}
