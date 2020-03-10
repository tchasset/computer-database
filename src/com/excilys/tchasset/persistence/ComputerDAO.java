package com.excilys.tchasset.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.tchasset.model.Computer;

public class ComputerDAO {

private Dao dao = Dao.getInstance();
	
	public Dao getDao() {
		return dao;
	}

	public List<Computer> getComputer() throws SQLException{
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT id,name,introduced,discontinued,company_id FROM computer;";
		ResultSet res = getDao().execute(query);
		while(res.next()) {
			Computer comp=new Computer();
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
			if(res.getDate("introduced")!=null)
				comp.setIntroduced(res.getDate("introduced").toLocalDate());
			if(res.getDate("discontinued")!=null)
				comp.setDiscontinued(res.getDate("discontinued").toLocalDate());
			comp.setCompany_id(res.getInt("company_id"));
			computers.add(comp);
		}
		return computers;
	}
	
	public void addComputer(Computer computer) {
		String query = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (?,?,?,?,?);";
		try {
			PreparedStatement statementComputer = getDao().conn.prepareStatement(query);
			statementComputer.setInt(1,computer.getId());
			statementComputer.setString(2,computer.getName());
			statementComputer.setDate(3,Date.valueOf(computer.getIntroduced()));
			statementComputer.setDate(4,Date.valueOf(computer.getDiscontinued()));
			statementComputer.setInt(5,computer.getCompany_id());
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComputer(int id) {
		String query = "DELETE FROM computer where id=?;";
		try {
			PreparedStatement statementComputer = getDao().conn.prepareStatement(query);
			statementComputer.setInt(1,id);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateComputer() {
		
	}
	
	public static void main(String[] args) throws SQLException {
    	Computer c = new Computer(1000,"abc",LocalDate.of(1995,7,30),LocalDate.of(2000,7,30),2);
    	ComputerDAO cd = new ComputerDAO();
    	//cd.addComputer(c);
    	cd.deleteComputer(1000);
    	for (Computer cc : cd.getComputer())
    		System.out.println(cc.toString());
    	
	}
}
