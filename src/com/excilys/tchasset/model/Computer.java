package com.excilys.tchasset.model;

import java.time.LocalDate;

public class Computer {
	
	private int id=0;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int company_id;
	
	public Computer() {}
	
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
 
	@Override
	public String toString() {
		return "Computer: "+name+" n°"+id+" introduced in "+introduced+" and discontinued in "+discontinued+" belonging to Company n°"+company_id;
	}
	
}
