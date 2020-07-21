package com.excilys.tchasset.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.QCompany;
import com.excilys.tchasset.model.QComputer;
import com.excilys.tchasset.persistence.interfaces.CompanyRepository;
import com.excilys.tchasset.persistence.interfaces.ComputerRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
@Transactional
public class CompanyDAO {

	@Autowired
	private CompanyRepository repo;
	@Autowired
	private ComputerRepository repoComputer;

	public List<Company> getCompanies() {
		Iterable<Company> iterable = repo.findAll();
		List<Company> companies = StreamSupport.stream(iterable.spliterator(), false)
	                                      		.collect(Collectors.toList());
		return companies;
	}
	
	public Optional<Company> getById(int id) {
		BooleanExpression bool = QCompany.company.id.eq(id);
		Optional<Company> computer = repo.findOne(bool);
		
		return computer;
	}
	
	public Optional<Company> getByName(String name) {
		BooleanExpression bool = QCompany.company.name.eq(name);
		Optional<Company> company = repo.findOne(bool);
		
		return company;
	}
	
	public void deleteCompany(int id) {
		Iterable<Computer> computers = repoComputer.findAll(QComputer.computer.company.id.eq(id));
		repoComputer.deleteAll(computers);
		repo.deleteById(id);
	}
}