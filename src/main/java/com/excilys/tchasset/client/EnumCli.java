package com.excilys.tchasset.client;

public enum EnumCli {
	LISTALLCOMPANY,
	LISTCOMPANY,
	LISTALLCOMPUTER,
	LISTCOMPUTER,
	ADDCOMPUTER,
	DELETECOMPUTER,
	UPDATECOMPUTER,
	DELETECOMPANY,
	EXIT;
	
	public static EnumCli value(int val) {
		switch(val) {
			case 1:
				return LISTALLCOMPANY;
			case 2:
				return LISTCOMPANY;
			case 3:
				return LISTALLCOMPUTER;
			case 4:
				return LISTCOMPUTER;
			case 5:
				return ADDCOMPUTER;
			case 6:
				return DELETECOMPUTER;
			case 7:
				return UPDATECOMPUTER;
			case 8:
				return DELETECOMPANY;
			default:
				return EXIT;
		}
	}
}
