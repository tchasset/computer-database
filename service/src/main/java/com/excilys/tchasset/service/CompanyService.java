package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.persistence.CompanyDAO;

@Service
public class CompanyService {

	private final CompanyDAO companyDAO;

	@Autowired
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}

	public Optional<Company> getById(int id) {
		return companyDAO.getById(id);
	}

	public Optional<Company> getByName(String name) {
		return companyDAO.getByName(name);
	}

	public boolean deleteCompany(int id) {
		return companyDAO.deleteCompany(id);
	}
}
