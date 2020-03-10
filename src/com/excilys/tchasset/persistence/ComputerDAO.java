package com.excilys.tchasset.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;

public class ComputerDAO {

	private static ComputerDAO instance;
	
	public static final ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			synchronized(ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
	            }
	        }
		}
	    return ComputerDAO.instance;
    }
	
	public List<Computer> getComputers(){
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company_id FROM computer LEFT JOIN company ON company_id;";
		try {
			Statement statement = Dao.getInstance().getConn().createStatement();
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				Computer comp=new Computer();
				comp.setId(res.getInt("id"));
				comp.setName(res.getString("name"));
				if(res.getDate("introduced")!=null)
					comp.setIntroduced(res.getDate("introduced").toLocalDate());
				if(res.getDate("discontinued")!=null)
					comp.setDiscontinued(res.getDate("discontinued").toLocalDate());
				comp.setCompany(new Company(res.getInt("company_id")));
				computers.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = Optional.empty();
		String query = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";
		try {
			PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query);
			statementComputer.setInt(1,id);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				Computer comp=new Computer();
				comp.setId(res.getInt("id"));
				comp.setName(res.getString("name"));
				if(res.getDate("introduced")!=null)
					comp.setIntroduced(res.getDate("introduced").toLocalDate());
				if(res.getDate("discontinued")!=null)
					comp.setDiscontinued(res.getDate("discontinued").toLocalDate());
				comp.setCompany(new Company(res.getInt("company_id")));
				computer = Optional.of(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}
	
	public void addComputer(Computer computer) {
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
		try {
			PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query);
			//statementComputer.setInt(1,computer.getId());
			statementComputer.setString(1,computer.getName());
			statementComputer.setDate(2,Date.valueOf(computer.getIntroduced()));
			statementComputer.setDate(3,Date.valueOf(computer.getDiscontinued()));
			statementComputer.setInt(4,computer.getCompany().getId());
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComputer(int id) {
		String query = "DELETE FROM computer where id=?;";
		try {
			PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query);
			statementComputer.setInt(1,id);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateComputer(Computer computer) {
		String query = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
		try {
			PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query);
			statementComputer.setInt(1,computer.getId());
			statementComputer.setString(2,computer.getName());
			statementComputer.setDate(3,Date.valueOf(computer.getIntroduced()));
			statementComputer.setDate(4,Date.valueOf(computer.getDiscontinued()));
			statementComputer.setInt(5,computer.getCompany().getId());
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
