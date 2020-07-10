package com.excilys.tchasset.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.model.QComputer;
import com.excilys.tchasset.persistence.interfaces.ComputerRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
@Transactional
public class ComputerDAO {
	
	@Autowired
	private ComputerRepository repo;

	/* @param page 		paginate request if not null
	 * @return			ALL computers with/without pagination
	 */
	public List<Computer> getAllComputers(Page page){
		if(page!=null) {
			return repo.findAll(PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
		}
		Iterable<Computer> iterable = repo.findAll();
		List<Computer> computers = StreamSupport.stream(iterable.spliterator(), false)
	                                      		.collect(Collectors.toList());
		return computers;
	}
	
	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * @return			ALL computers alphebetical ordered by name ASC or DESC
	 */
	public List<Computer> getComputersOrderByComputer(Page page, String order){
		PageRequest p=null;
		if(order.equals("ASC")) {
			p = PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by("name").ascending());
		}
		if(order.equals("DESC")) {
			p = PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by("name").descending());
		}
		if(p==null) {
			return new ArrayList<>();
		}
		return repo.findAll(p).getContent();
	}
	
	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC) 
	 * @return 			ALL computers alphebetical ordered by company name ASC or DESC
	 */
	public List<Computer> getComputersOrderByCompany(Page page, String order){
		PageRequest p=null;
		if(order.equals("ASC")) {
			p = PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by("company.name").ascending());
		}
		if(order.equals("DESC")) {
			p = PageRequest.of(page.getCurrentPage()-1,page.getSize(),Sort.by("company.name").descending());
		}
		if (p==null){
			return new ArrayList<>();
		}
		return repo.findAll(p).getContent();
	}
	
	/* @param id	searching computer by it 
	 * @return 		computer with given ID if it exists
	 */
	public Optional<Computer> getById(int id) {
		BooleanExpression bool = QComputer.computer.id.eq(id);
		Optional<Computer> computer = repo.findOne(bool);
		
		return computer;
	}
	
	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given name (company or computer name)
	 */	
	public List<Computer> getByAllName(Page page, String name) {
		BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(name)
														.or(QComputer.computer.company.name.containsIgnoreCase(name));

		return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
	}
	
	public int getNbBySearch(String name) {
		BooleanExpression bool = QComputer.computer.name.containsIgnoreCase(name)
							.or(QComputer.computer.company.name.containsIgnoreCase(name));
		long nb = repo.count(bool);
		return (int) nb;
	}
	
	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given name
	 */
	public List<Computer> getByName(Page page, String name) {
		BooleanExpression bool = QComputer.computer.name.eq(name);

		return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
	}
	
	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given company name
	 */
	public List<Computer> getByCompany(Page page, String name){
		BooleanExpression bool = QComputer.computer.company.name.eq(name);

		return repo.findAll(bool,PageRequest.of(page.getCurrentPage()-1,page.getSize())).getContent();
	}
	
	/* Add the computer to the Database
	 * @param computer		computer to add
	 */
	public void addComputer(Computer computer) {
		repo.save(computer);
	}
	
	/* Update the computer 
	 * @param computer		computer to update
	 */
	public void updateComputer(Computer computer) {
		repo.save(computer);
	}
	
	/* Delete the computer with given ID
	 * @param id 			id of the computer to delete
	 */
	public void deleteComputer(int id) {
		repo.deleteById(id);
	}
	
	/* @return	Number of computers in the database
	 */
	public int getNbComputers() {
		long nb = repo.count();
		return (int) nb;
	}
}