package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.persistence.ComputerDAO;

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	
	public List<Computer> getAllComputers(Page page) {
		return computerDAO.getAllComputers(page);
	}
	
	public List<Computer> getComputersOrderByComputer(Page page, String order) {
		return computerDAO.getComputersOrderByComputer(page, order);
	}
	
	public List<Computer> getComputersOrderByCompany(Page page, String order){
		return computerDAO.getComputersOrderByCompany(page, order);
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = computerDAO.getById(id);
		return computer;
	}
	
	public List<Computer> getByAllName(Page page, String name) {
		return computerDAO.getByAllName(page, name);
	}
	
	public List<Computer> getByName(Page page, String name) {
		return computerDAO.getByName(page, name);
	}
	
	public List<Computer> getByCompany(Page page, String name) {
		return computerDAO.getByCompany(page, name);
	}
	
	public boolean addComputer(Computer computer) {
		return computerDAO.addComputer(computer);
	}
	
	public boolean updateComputer(Computer computer) {
		return computerDAO.updateComputer(computer);
	}
	
	public boolean deleteComputer(int id) {
		return computerDAO.deleteComputer(id);
	}
	
	public int getNbBySearch(String name) {
		return computerDAO.getNbBySearch(name);
	}
	
	public int getNbComputers() {
		return computerDAO.getNbComputers();
	}
}