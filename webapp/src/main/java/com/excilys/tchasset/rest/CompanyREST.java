package com.excilys.tchasset.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.mapper.CompanyMapper;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyREST {

	private CompanyService companyService;
	
	@Autowired
	public CompanyREST(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getCompanies () {
		try {
			List<Company> companies = companyService.getCompanies();
	        if (!companies.isEmpty()) {
	            return new ResponseEntity<>(companies, HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
    }
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById (@PathVariable("id") int id) {

		try {
			Optional<Company> company = companyService.getById(id);
			if (company.isPresent()) {
				return new ResponseEntity<>(CompanyMapper.toDTO(company.get()).get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@DeleteMapping(path = "/{id}")
    public ResponseEntity<Company> deleteCompany (@PathVariable("id") int id) {
		try {
			if(companyService.deleteCompany(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
    }
}
