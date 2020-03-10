package com.excilys.tchasset.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.excilys.tchasset.model.Company;

public class CompanyDAO {
	
	private Dao dao = Dao.getInstance();
	
	public Dao getDao() {
		return dao;
	}

	public List<Company> getCompanies() throws SQLException{
		List<Company> companies = new ArrayList<Company>();
		String query = "SELECT id,name FROM company;";
		ResultSet res = getDao().execute(query);
		while(res.next()) {
			Company comp=new Company();
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
			companies.add(comp);
		}
		return companies;
	}
	
    
    public static void main(String[] args) throws SQLException {
    	CompanyDAO cd = new CompanyDAO();
    	for (Company c : cd.getCompanies())
    		System.out.println(c.toString());
	}
}
