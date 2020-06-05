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
		
		CompanyService companyService   = CompanyService.getInstance();
		ComputerService computerService = ComputerService.getInstance()
;		
		String name = request.getParameter("computerName");
		
		String date = request.getParameter("introduced");
		LocalDate introduced = (date == "" ? null : LocalDate.parse(date));
		
		date = request.getParameter("discontinued");
		LocalDate discontinued = (date == "" ? null : LocalDate.parse(date));
		
		String companyId = request.getParameter("companyId");
		int id = Integer.valueOf(companyId);
		
		Optional<Company> companyOpt = companyService.getById(id);
		Company company = companyOpt.isPresent() ? companyOpt.get() : null;
		
		computerService.addComputer(new Computer.Builder().setName(name)
												.setIntroduced(introduced)
												.setDiscontinued(discontinued)
												.setCompany(company).build());
	}
}
