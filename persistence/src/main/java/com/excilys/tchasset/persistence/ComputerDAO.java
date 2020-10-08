package com.excilys.tchasset.persistence;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.model.QComputer;
import com.excilys.tchasset.persistence.interfaces.ComputerRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Transactional
public class ComputerDAO {

	private final ComputerRepository repo;

	@Autowired
	public ComputerDAO(ComputerRepository repo) {
		this.repo = repo;
	}

	/* @param page 		paginate request if not null
	 * @return			ALL computers with/without pagination
	 */
	public List<Computer> getAllComputers(Page page){
		try {
			if(page!=null) {
				return repo.findAll(PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
			}
			Iterable<Computer> iterable = repo.findAll();
			return StreamSupport.stream(iterable.spliterator(), false)
					.collect(Collectors.toList());
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	private Optional<PageRequest> ordering(Page page, String order, String sortBy) {
		if(order.equals("ASC")) {
			return Optional.of(PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by(sortBy).ascending()));
		}
		if(order.equals("DESC")) {
			return Optional.of(PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by(sortBy).descending()));
		}
		return Optional.empty();
	}

	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * @return			ALL computers alphebetical ordered by name ASC or DESC
	 */
	public List<Computer> getComputersOrderByComputer(Page page, String order, String search){
		Optional<PageRequest> p;
		if(!search.isEmpty()) {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(search);
			p = ordering(page, order, "name");
			return p.map(pageRequest -> repo.findAll(bool, pageRequest).getContent()).orElse(Collections.emptyList());
		}
		else {
			p = ordering(page, order, "name");
			return p.map(pageRequest -> repo.findAll(pageRequest).getContent()).orElseGet(Collections::emptyList);
		}

	}

	public List<Computer> getComputersOrderByIntroduced(Page page, String order, String search){
		Optional<PageRequest> p;
		if(!search.isEmpty()) {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(search);
			p = ordering(page, order, "introduced");
			return p.map(pageRequest -> repo.findAll(bool, pageRequest).getContent()).orElse(Collections.emptyList());
		}
		else {
			p = ordering(page, order, "introduced");
			return p.map(pageRequest -> repo.findAll(pageRequest).getContent()).orElse(Collections.emptyList());
		}
	}

	public List<Computer> getComputersOrderByDiscontinued(Page page, String order, String search){
		Optional<PageRequest> p;
		if(!search.isEmpty()) {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(search);
			p = ordering(page, order, "discontinued");
			return p.map(pageRequest -> repo.findAll(bool, pageRequest).getContent()).orElse(Collections.emptyList());
		}
		else {
			p = ordering(page, order, "discontinued");
			return p.map(pageRequest -> repo.findAll(pageRequest).getContent()).orElse(Collections.emptyList());
		}
	}

	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * @return 			ALL computers alphebetical ordered by company name ASC or DESC
	 */
	public List<Computer> getComputersOrderByCompany(Page page, String order, String search){
		Optional<PageRequest> p;
		if(!search.isEmpty()) {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(search);
			p = ordering(page, order, "company.name");
			return p.map(pageRequest -> repo.findAll(bool, pageRequest).getContent()).orElse(Collections.emptyList());
		}
		else {
			p = ordering(page, order, "company.name");
			return p.map(pageRequest -> repo.findAll(pageRequest).getContent()).orElse(Collections.emptyList());
		}
	}

	/* @param id	searching computer by it
	 * @return 		computer with given ID if it exists
	 */
	public Optional<Computer> getById(int id) {
		try {
			BooleanExpression bool = QComputer.computer.id.eq(id);
			return repo.findOne(bool);
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	/* @param page 		paginate request if not null
	 * @param name		searching computer by it
	 * @return 			computers with given name (company or computer name)
	 */
	public List<Computer> getByAllName(Page page, String name) {
		try {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(name);
			return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}

	}

	public int getNbBySearch(String name) {
		try {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(name);
			long nb = repo.count(bool);
			return (int) nb;
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	/* @param page 		paginate request if not null
	 * @param name		searching computer by it
	 * @return 			computers with given name
	 */
	public List<Computer> getByName(Page page, String name) {
		try {
			BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(name);
			return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	/* @param page 		paginate request if not null
	 * @param name		searching computer by it
	 * @return 			computers with given company name
	 */
	public List<Computer> getByCompany(Page page, String name){
		BooleanExpression bool = QComputer.computer.company.name.containsIgnoreCase(name);

		return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
	}

	/* Add the computer to the Database
	 * @param computer		computer to add
	 */
	public boolean addComputer(Computer computer) {
		try {
			repo.save(computer);
			return true;
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			return false;
		}
	}

	/* Update the computer
	 * @param computer		computer to update
	 */
	public boolean updateComputer(Computer computer) {
		try {
			repo.save(computer);
			return true;
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			return false;
		}
	}

	/* Delete the computer with given ID
	 * @param id 			id of the computer to delete
	 */
	public boolean deleteComputer(int id) {
		try {
			if (getById(id).isPresent()) {
				repo.deleteById(id);
				return true;
			}
			return false;
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}

	/* @return	Number of computers in the database
	 */
	public int getNbComputers() {
		try {
			long nb = repo.count();
			return (int) nb;
		} catch (DataAccessException ex) {
			Logging.error("Problem with db", getClass());
			throw ex;
		}
	}
}
