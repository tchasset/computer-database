package com.excilys.tchasset.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.ComputerService;
import com.excilys.tchasset.validator.ComputerValidation;

@RestController
@RequestMapping(value = "/computers")
public class ComputerREST {

	private ComputerService computerService;
	private Page pages;

	@Autowired
	public ComputerREST(ComputerService computerService, Page pages) {
		this.computerService = computerService;
		this.pages = pages;
	}

	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getComputers (@QueryParam("page") int page) {

		pages.setCurrentPage(page);
		try {
			List<ComputerDTO> computers = computerService.getAllComputers(pages).stream()
					.map(computer -> ComputerMapper.toDTO(computer))
					.collect(Collectors.toList());
			if (!computers.isEmpty()) {
				return new ResponseEntity<>(computers, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ComputerDTO> getComputerById (@PathVariable("id") int id) {

		try {
			Optional<Computer> computer = computerService.getById(id);
			if (computer.isPresent()) {
				return new ResponseEntity<>(ComputerMapper.toDTO(computer.get()), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(path = "/search")
	public ResponseEntity<List<ComputerDTO>> getComputerByName (@QueryParam("name") String name, @QueryParam("page") int page) {

		pages.setCurrentPage(page);
		try {
			List<ComputerDTO> computers = computerService.getByAllName(pages, name).stream()
					.map(computer -> ComputerMapper.toDTO(computer))
					.collect(Collectors.toList());
			if (!computers.isEmpty()) {
				return new ResponseEntity<>(computers, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(path = "/nb")
	public ResponseEntity<HashMap<String, Integer>> getNbComputers () {
		try {
			int nb = computerService.getNbComputers();
			HashMap<String, Integer> mappy = new HashMap<String, Integer>(1);
			mappy.put("nb", nb);
			return new ResponseEntity<>(mappy, HttpStatus.OK);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/nbsearch")
	public ResponseEntity<HashMap<String, Integer>> getNbBySearch(String name) {
		try {
			int nb = computerService.getNbBySearch(name);
			HashMap<String, Integer> mappy = new HashMap<String, Integer>(1);
			mappy.put("nb", nb);
			return new ResponseEntity<>(mappy, HttpStatus.OK);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<ComputerDTO> addComputer (@RequestBody ComputerDTO computerDTO) {

		if(ComputerValidation.checkValidity(computerDTO)) {
			try {
				if ( computerService.addComputer(ComputerMapper.fromDTO(computerDTO)) ) {
					return new ResponseEntity<>(HttpStatus.OK);
				}
			} catch (DataAccessException ex) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
	}

	@PutMapping
	public ResponseEntity<ComputerDTO> editComputer (@RequestBody ComputerDTO computerDTO) {

		if(ComputerValidation.checkValidity(computerDTO)) {
			try {
				if( computerService.updateComputer(ComputerMapper.fromDTO(computerDTO)) ) {
					return new ResponseEntity<>(HttpStatus.OK);
				}
			} catch (DataAccessException ex) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<ComputerDTO> deleteComputer (@PathVariable("id") int id) {
		try {
			if(computerService.deleteComputer(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
