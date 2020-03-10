package com.excilys.tchasset.model;

public class Company {

	private int id=0;
	private String name;
	
	public Company() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Company n°"+id+": "+name;
	}
	
}
