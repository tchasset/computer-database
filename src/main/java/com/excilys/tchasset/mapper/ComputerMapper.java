package com.excilys.tchasset.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;

@Component
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
	
	/*
	 * @param computerDTO	DTO to convert into bean
	 * 
	 * @return 				The computer bean from a computer DTO
	 */
	public Computer fromDTO(ComputerDTO computerDTO) {
		Computer computer;
		
		int id = Integer.valueOf(computerDTO.getId());
		String name = computerDTO.getName();
		LocalDate introduced   = (computerDTO.getIntroduced() == "" ? null : LocalDate.parse(computerDTO.getIntroduced()));
		LocalDate discontinued = (computerDTO.getDiscontinued() == "" ? null : LocalDate.parse(computerDTO.getDiscontinued()));
		Company company = CompanyMapper.getInstance().fromDTO(computerDTO.getCompanyDTO());
		
		computer = new Computer.Builder().setId(id).setName(name).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
		
		return computer;
	}
	
	/*
	 * @param computerDTO	Bean to convert into DTO
	 * 
	 * @return 				The computer DTO from a computer bean
	 */
	public ComputerDTO toDTO(Computer computer) {
		ComputerDTO computerDTO;
		
		String name = computer.getName();
		String introduced = String.valueOf(computer.getIntroduced());
		String discontinued = String.valueOf(computer.getDiscontinued());
		CompanyDTO companyDTO = CompanyMapper.getInstance().toDTO(computer.getCompany());
		
		computerDTO = new ComputerDTO.Builder().setName(name).setIntroduced(introduced).setDiscontinued(discontinued).setCompanyDTO(companyDTO).build();
		
		return computerDTO;
	}
	
	@Deprecated
	public List<Computer> sorted(List<Computer> computer) {
		computer.sort((Computer c1, Computer c2)->c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase()));
		return computer;
	}
	
	/*
	 * @param res	result of a SQL request
	 * 
	 * @return 		The computer found by the request
	 */
	public Computer getComputer(ResultSet res) {
		Computer comp=new Computer();
		try {
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
			if(res.getDate("introduced")!=null)
				comp.setIntroduced(res.getDate("introduced").toLocalDate());
			if(res.getDate("discontinued")!=null)
				comp.setDiscontinued(res.getDate("discontinued").toLocalDate());
			comp.setCompany(new Company.Builder().setId(res.getInt("company.id")).setName(res.getString("company.name")).build());
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return comp;
	}
}