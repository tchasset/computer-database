package com.excilys.tchasset.mapper;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;

public class ComputerMapper {
	
	/*
	 * @param computerDTO	DTO to convert into bean
	 * 
	 * @return 				The computer bean from a computer DTO
	 */
	public static Computer fromDTO(ComputerDTO computerDTO) {
		Computer computer;
		
		int id = Integer.valueOf(computerDTO.getId());
		String name = computerDTO.getComputerName();
		LocalDate introduced   = (computerDTO.getIntroduced() == "" ? null : LocalDate.parse(computerDTO.getIntroduced()));
		LocalDate discontinued = (computerDTO.getDiscontinued() == "" ? null : LocalDate.parse(computerDTO.getDiscontinued()));
		Optional<Company> comp = CompanyMapper.fromDTO(computerDTO.getCompanyDTO());
		Company company = comp.isPresent() ? comp.get() : null;
		
		computer = new Computer.Builder(name).setId(id).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
		
		return computer;
	}
	
	/*
	 * @param computerDTO	Bean to convert into DTO
	 * 
	 * @return 				The computer DTO from a computer bean
	 */
	public static ComputerDTO toDTO(Computer computer) {
		ComputerDTO computerDTO;
		
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String introduced = String.valueOf(computer.getIntroduced());
		String discontinued = String.valueOf(computer.getDiscontinued());
		Optional<CompanyDTO> comp = CompanyMapper.toDTO(computer.getCompany());
		CompanyDTO companyDTO = comp.isPresent() ? comp.get() : null;

		computerDTO = new ComputerDTO.Builder(name).setId(id).setIntroduced(introduced).setDiscontinued(discontinued).setCompanyDTO(companyDTO).build();
		
		return computerDTO;
	}
}