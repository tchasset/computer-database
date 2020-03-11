package com.excilys.tchasset.cli;

import java.sql.Date;
import java.time.LocalDate;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.persistence.ComputerDAO;
import com.excilys.tchasset.service.CompanyService;

public class App {

	public static void main(String[] args) {
		Computer c = new Computer.Builder().setName("test2").setIntroduced(LocalDate.of(1980,10,1)).build();
//    	ComputerDAO.getInstance().addComputer(c);
		System.out.println(c.getDiscontinued());
    	for (Computer cc : ComputerDAO.getInstance().getComputers())
    		System.out.println(cc.toString());
//		CompanyService cs = new CompanyService();
//		System.out.println(cs.getById(2));
	}
}
