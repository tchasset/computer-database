package com.excilys.tchasset.rest;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.mapper.UserMapper;
import com.excilys.tchasset.model.User;
import com.excilys.tchasset.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserREST {

	private UserService userService;

	@Autowired
	public UserREST(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<UserDTO> usersList = userService.getUsers().stream()
				.map(UserMapper::toDTO)
				.collect(Collectors.toList());
		return new ResponseEntity(usersList,HttpStatus.OK);
	}

	@GetMapping("login")
	public ResponseEntity<User> connection(@QueryParam("username") String username,
										   @QueryParam("password") String password){
		UserDTO userDTO = new UserDTO.Builder().setUsername(username).setPassword(password).build();
		User user = userService.findByUsername(username);
		if(userService.getUser(userDTO).isPresent()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<User> addUser(@QueryParam("username") String username,
										@QueryParam("password") String password){

		User user = new User.Builder().setUsername(username)
				.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()))
				.setRole("USER")
				.build();
		if(userService.addUser(user)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/connectInfo")
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
