package com.excilys.tchasset.mapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.persistence.Dao;

public class ComputerMapper {
	
	private static ComputerMapper instance;
	
	public static final ComputerMapper getInstance() {
		if (ComputerMapper.instance == null) {
			synchronized(ComputerMapper.class) {
				if (ComputerMapper.instance == null) {
					ComputerMapper.instance = new ComputerMapper();
	            }
	        }
		}
	    return ComputerMapper.instance;
    }
	
	public Computer getComputer(ResultSet res) {
		Computer comp=new Computer();
		try {
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
			if(res.getDate("introduced")!=null)
				comp.setIntroduced(res.getDate("introduced").toLocalDate());
			if(res.getDate("discontinued")!=null)
				comp.setDiscontinued(res.getDate("discontinued").toLocalDate());
			comp.setCompany(new Company.Builder().setId(res.getInt("company_id")).build());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comp;
	}
	
	public void manageComputer(Computer computer, String query) {
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setString(1,computer.getName());
			statementComputer.setDate(2,Date.valueOf(computer.getIntroduced()));
			statementComputer.setDate(3,Date.valueOf(computer.getDiscontinued()));
			if(computer.getCompany()!=null)
				statementComputer.setInt(4,computer.getCompany().getId());
			else
				statementComputer.setNull(4, java.sql.Types.BIGINT);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
