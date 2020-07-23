package com.excilys.tchasset.rest;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserDataREST {

	@GetMapping
	public ResponseEntity<HashMap<String, String>> getCompanies(Authentication authentication) {
		HashMap<String, String> return_value = new HashMap<String, String>();
		if (!(authentication == null)) {
			return_value.put("name", authentication.getName());
			return_value.put("authority", authentication.getAuthorities().toString());
			return new ResponseEntity<>(return_value, HttpStatus.OK);
		} else {
			return_value.put("name", "guest");
			return_value.put("authority", "[NONE]");
			return new ResponseEntity<>(return_value, HttpStatus.OK);
		}
	}

}
