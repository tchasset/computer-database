package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.persistence.CompanyDAO;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyDAO companyDAO;
	
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}
	
	public List<Company> getCompaniesOrderBy(String order) {
		return companyDAO.getCompaniesOrderBy(order);
	}
	
	public Optional<Company> getById(int id) {
		Optional<Company> company = companyDAO.getById(id);
		return company;
	}
	
	public Optional<Company> getByName(String name) {
		Optional<Company> company = companyDAO.getByName(name);
		return company;
	}
	
	public void deleteCompany(int id) {
		companyDAO.deleteCompany(id);
	}
}