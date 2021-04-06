package com.excilys.tchasset.rest;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.mapper.UserMapper;
import com.excilys.tchasset.model.User;
import com.excilys.tchasset.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserREST {

	private final UserService userService;

	@Autowired
	public UserREST(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<UserDTO> usersList = userService.getUsers().stream()
				.map(UserMapper::toDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(usersList,HttpStatus.OK);
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
		if(userService.getUser(UserMapper.toDTO(user)).isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		else if(userService.addUser(user)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
