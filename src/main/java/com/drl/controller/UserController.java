package com.drl.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drl.config.AppConstantUtil;
import com.drl.entities.User;
import com.drl.service.AuthenticateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstantUtil.USER_PATH)
public class UserController {

	@Autowired
	private AuthenticateService service;
	
	 @PostMapping("/create")
	    public ResponseEntity<?> createUser(@RequestBody @Valid User userCreateRequest) throws Exception {
	        	return service.createUser(userCreateRequest);
	        }
	       
	@GetMapping("/getUsers")
		public ResponseEntity<?> getUserList() {
	        try {
	            // Fetch all users from the service
	            List<User> users = service.getAllUsers();
	            // Return response
	            return ResponseEntity.ok(users);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Error fetching users");
	        }
	    }
	}

