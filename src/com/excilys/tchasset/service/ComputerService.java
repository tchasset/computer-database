package com.excilys.tchasset.service;

import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.persistence.ComputerDAO;

public class ComputerService {
	
	public List<Computer> getCompanies() {
		List<Computer> companies = ComputerDAO.getInstance().getComputers();
		return companies;
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = ComputerDAO.getInstance().getById(id);
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
}
