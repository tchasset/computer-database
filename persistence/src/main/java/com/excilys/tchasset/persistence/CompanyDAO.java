package com.excilys.tchasset.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.QCompany;
import com.excilys.tchasset.model.QComputer;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
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
	
	public List<Company> getCompaniesOrderBy(String order) {
		Iterable<Company> iterable=null;
		if(order.equals("ASC")) {
			iterable = repo.findAll(Sort.by("name").ascending());
		}
		if(order.equals("DESC")) {
			iterable = repo.findAll(Sort.by("name").ascending());
		}
		if(iterable==null) {
			return new ArrayList<>();
		}
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
	
	@Transactional
	public void deleteCompany(int id) {
		Iterable<Computer> computers = repoComputer.findAll(QComputer.computer.company.id.eq(id));
		repoComputer.deleteAll(computers);
		repo.deleteById(id);
	}
}