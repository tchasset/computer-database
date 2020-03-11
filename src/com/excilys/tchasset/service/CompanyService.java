package com.excilys.tchasset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.persistence.CompanyDAO;

public class CompanyService {
	
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		companies = CompanyDAO.getInstance().getCompanies();
		return companies;
	}
	
	public Optional<Company> getById(int id) {
		Optional<Company> company = CompanyDAO.getInstance().getById(id);
		return company;
	}
}
