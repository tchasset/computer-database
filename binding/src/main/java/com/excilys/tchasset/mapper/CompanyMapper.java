package com.excilys.tchasset.mapper;

import java.util.Optional;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.model.Company;

public class CompanyMapper {

	/*
	 * @param company	bean to convert into DTO
	 *
	 * @return 			The company bean to a company DTO
	 */
	public static Optional<CompanyDTO> toDTO(Company company) {
		Optional<CompanyDTO> companyDTO=Optional.empty();

		if(company!=null) {
			String id = String.valueOf(company.getId());
			String name = company.getName();
			companyDTO = Optional.of(new CompanyDTO.Builder(name).setId(id).build());
		}
		return companyDTO;
	}

	/*
	 * @param companyDTO	DTO to convert into bean
	 *
	 * @return 				The company bean from a company DTO
	 */
	public static Optional<Company> fromDTO(CompanyDTO companyDTO) {
		Optional<Company> company = Optional.empty();

		if(companyDTO!=null) {
			int id = Integer.parseInt(companyDTO.getId());
			String name = companyDTO.getName();
			company = Optional.of(new Company.Builder(name).setId(id).build());
		}
		return company;
	}
}
