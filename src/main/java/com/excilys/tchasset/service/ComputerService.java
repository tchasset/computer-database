package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.model.Computer;
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
	
	public List<Computer> getComputers() {
		List<Computer> companies = ComputerDAO.getInstance().getComputers();
		return companies;
	}
	
	public List<Computer> getComputersOrderByComputer(String order) {
		return ComputerDAO.getInstance().getComputersOrderByComputer(order);
	}
	
	public List<Computer> getComputersOrderByCompany(String order){
		return ComputerDAO.getInstance().getComputersOrderByCompany(order);
	}
	
	public List<Computer> getComputersPaginate(int current, int sizeByPage) {
		List<Computer> companies = ComputerDAO.getInstance().getComputersPaginate(current, sizeByPage);
		return companies;
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = ComputerDAO.getInstance().getById(id);
		return computer;
	}
	
	public Optional<Computer> getByName(String name) {
		Optional<Computer> computer = ComputerDAO.getInstance().getByName(name);
		return computer;
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
	
	public List<Computer> getByCompany(String name) {
		return ComputerDAO.getInstance().getByCompany(name);
	}
}
