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

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static CompanyMapper companyMapper = SpringConfig.getContext().getBean(CompanyMapper.class);
	private static ComputerMapper computerMapper = SpringConfig.getContext().getBean(ComputerMapper.class);
	private static ComputerService computerService = SpringConfig.getContext().getBean(ComputerService.class);
	private static CompanyService companyService = SpringConfig.getContext().getBean(CompanyService.class);

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		List<Company> companies = companyService.getCompanies();
		
		request.setAttribute("companyName", companies);
			
		getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,response);
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		Computer computer;
		
		String name 		= request.getParameter("computerName");
		String introduced 	= request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId	= request.getParameter("companyId");

		Optional<Company> company = companyService.getById(Integer.valueOf(companyId));
		CompanyDTO companyDTO = company.isPresent() ? companyMapper.toDTO(company.get()) : null;
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("0")
															.setName(name)
														   	.setIntroduced(introduced)
															.setDiscontinued(discontinued)
															.setCompanyDTO(companyDTO).build();
		
		ComputerValidation.checkValidity(computerDTO);
		if(ComputerValidation.messageError.isEmpty()) {
			computer = computerMapper.fromDTO(computerDTO);
			
			computerService.addComputer(computer);
			response.sendRedirect("dashboard?addSuccess=1");
		}
		else {
			request.setAttribute("error", ComputerValidation.messageError);
			doGet(request, response);	
		}
	}
}
