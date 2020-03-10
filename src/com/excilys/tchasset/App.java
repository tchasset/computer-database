package com.excilys.tchasset;

import java.time.LocalDate;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.persistence.ComputerDAO;

public class App {

	public static void main(String[] args) {
		Computer c = new Computer("abc",LocalDate.of(1995,7,30),LocalDate.of(2000,7,30),new Company());
    	System.out.println(ComputerDAO.getInstance().getById(1000));
    	//ComputerDAO.getInstance().addComputer(c);
    	//ComputerDAO.getInstance().deleteComputer(1002);
    	for (Computer cc : ComputerDAO.getInstance().getComputers())
    		System.out.println(cc.toString());
	}
}
