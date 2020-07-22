package com.excilys.tchasset.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		
		List<Company> companies = companyService.getCompanies();
        if (!companies.isEmpty()) {
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@DeleteMapping(path = "/{id}")
    public ResponseEntity<Company> deleteCompany (@PathVariable("id") int id) {
		
		companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
