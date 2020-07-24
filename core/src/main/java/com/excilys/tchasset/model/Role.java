package com.excilys.tchasset.model;

public enum Role {
	ADMIN ("ADMIN"),
	USER ("USER");
	
	private final String role;

	private Role (String str) {
		this.role = str;
	}
	
	public String toString() {
		return this.role;
	}
}

