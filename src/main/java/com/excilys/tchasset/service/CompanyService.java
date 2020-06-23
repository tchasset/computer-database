package com.excilys.tchasset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.persistence.CompanyDAO;

@Service
public class CompanyService {
	
private static CompanyService instance;
	
	public static final CompanyService getInstance() {
		if (CompanyService.instance == null) {
			synchronized(CompanyService.class) {
				if (CompanyService.instance == null) {
					CompanyService.instance = new CompanyService();
	            }
	        }
		}
	    return CompanyService.instance;
    }
	
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		companies = CompanyDAO.getInstance().getCompanies();
		return companies;
	}
	
	public List<Company> getCompaniesOrderBy(String order) {
		return CompanyDAO.getInstance().getCompaniesOrderBy(order);
	}
	
	public Optional<Company> getById(int id) {
		Optional<Company> company = CompanyDAO.getInstance().getById(id);
		return company;
	}
	
	public Optional<Company> getByName(String name) {
		Optional<Company> company = CompanyDAO.getInstance().getByName(name);
		return company;
	}
	
	public void deleteCompany(int id) {
		CompanyDAO.getInstance().deleteCompany(id);
	}
}