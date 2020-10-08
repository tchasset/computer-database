package com.excilys.tchasset.persistence;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.QCompany;
import com.excilys.tchasset.model.QComputer;
import com.excilys.tchasset.persistence.interfaces.CompanyRepository;
import com.excilys.tchasset.persistence.interfaces.ComputerRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CompanyDAO {

	private final CompanyRepository repo;
	private final ComputerRepository repoComputer;

	@Autowired
	public CompanyDAO(CompanyRepository repo, ComputerRepository repoComputer) {
		this.repo = repo;
		this.repoComputer = repoComputer;
	}

	public List<Company> getCompanies() {
		try {
			Iterable<Company> iterable = repo.findAll();
			return StreamSupport.stream(iterable.spliterator(), false)
					.collect(Collectors.toList());
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	public Optional<Company> getById(int id) {
		try {
			BooleanExpression bool = QCompany.company.id.eq(id);

			return repo.findOne(bool);
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	public Optional<Company> getByName(String name) {
		BooleanExpression bool = QCompany.company.name.eq(name);

		return repo.findOne(bool);
	}

	@Transactional
	public boolean deleteCompany(int id) {
		if(getById(id).isPresent()) {
			Iterable<Computer> computers = repoComputer.findAll(QComputer.computer.company.id.eq(id));
			repoComputer.deleteAll(computers);
			repo.deleteById(id);
			return true;
		}
		return false;
	}
}
