package com.excilys.tchasset.mapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.tchasset.log.Logging;
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
			Logging.error(e.getMessage());
		}
		return comp;
	}
	
	/*
	 * Gère les cas, ou les champs, lors de l'ajout ou la modification d'un ordinateur
	 * sont à null puis execute la requete SQL
	 */
	public void manageComputer(Computer computer, String query) {
		if(!computer.getName().isEmpty()) {
			try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
				statementComputer.setString(1,computer.getName());
				manageDate(statementComputer, computer);
				manageCompany(statementComputer, computer);
				statementComputer.executeUpdate();
			} catch (SQLException e) {
				Logging.error(e.getMessage());
			}
		}
	}
	
	/*
	 * Verifie si les dates sont null ou non pour effectuer l'ajout/modif en base
	 */
	private void manageDate(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if (computer.getIntroduced()!=null)
			statementComputer.setDate(2,Date.valueOf(computer.getIntroduced()));
		else
			statementComputer.setNull(2, java.sql.Types.TIMESTAMP);
		if (computer.getDiscontinued()!=null)
			statementComputer.setDate(3,Date.valueOf(computer.getDiscontinued()));
		else
			statementComputer.setNull(3, java.sql.Types.TIMESTAMP);
	}
	
	/*
	 * Verifie si la compagnie est null ou non pour effectuer l'ajout/modif en base
	 */
	private void manageCompany(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if(computer.getCompany()!=null)
			statementComputer.setInt(4,computer.getCompany().getId());
		else
			statementComputer.setNull(4, java.sql.Types.BIGINT);
	}
}
