package com.excilys.tchasset.persistence;

public enum EnumQuery {
	
	SELECTCOMPUTER	("SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					+ "FROM computer "
					+ "LEFT JOIN company "
					+ "ON computer.company_id = company.id"),
	
	INSERTCOMPUTER	("INSERT INTO computer (name, introduced, discontinued, company_id, id) VALUES (?,?,?,?,?)"),
	
	UPDATECOMPUTER	("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?"),
	
	DELETECOMPUTER	("DELETE FROM computer WHERE id=?"),
	
	COUNTCOMPUTER	("SELECT COUNT(*) as 'Computers' FROM computer"),
	
	SELECTCOMPANY	("SELECT id, name FROM company"),

	LIMIT			(" LIMIT ?,?");

	private String name="";
	
	EnumQuery(String name) {
		this.name = name;
	}
   
	public String toString(){
		return name;
	}
}
