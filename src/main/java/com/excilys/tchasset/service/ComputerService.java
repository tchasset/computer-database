package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.persistence.ComputerDAO;

public class ComputerService {
	
	private static ComputerService instance;
	
	public static final ComputerService getInstance() {
		if (ComputerService.instance == null) {
			synchronized(ComputerService.class) {
				if (ComputerService.instance == null) {
					ComputerService.instance = new ComputerService();
	            }
	        }
		}
	    return ComputerService.instance;
    }
	
	public List<Computer> getAllComputers(Page page) {
		return ComputerDAO.getInstance().getAllComputers(page);
	}
	
	public List<Computer> getComputersOrderByComputer(Page page, String order) {
		return ComputerDAO.getInstance().getComputersOrderByComputer(page, order);
	}
	
	public List<Computer> getComputersOrderByCompany(Page page, String order){
		return ComputerDAO.getInstance().getComputersOrderByCompany(page, order);
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = ComputerDAO.getInstance().getById(id);
		return computer;
	}
	
	public List<Computer> getByAllName(Page page, String name) {
		return ComputerDAO.getInstance().getByAllName(page, name);
	}
	
	public List<Computer> getByName(Page page, String name) {
		return ComputerDAO.getInstance().getByName(page, name);
	}
	
	public List<Computer> getByCompany(Page page, String name) {
		return ComputerDAO.getInstance().getByCompany(page, name);
	}
	
	public void addComputer(Computer computer) {
		ComputerDAO.getInstance().addComputer(computer);
	}
	
	public void updateComputer(Computer computer) {
		ComputerDAO.getInstance().updateComputer(computer);
	}
	
	public void deleteComputer(int id) {
		ComputerDAO.getInstance().deleteComputer(id);
	}
	
	public int getNbComputers() {
		return ComputerDAO.getInstance().getNbComputers();
	}
}
