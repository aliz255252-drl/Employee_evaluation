package com.drl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drl.config.AppConstantUtil;
import com.drl.entities.User;
import com.drl.service.AuthenticateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstantUtil.AUTH_PATH)
public class AuthenticationController {

	@Autowired
	private AuthenticateService service;
	
	
	@PostMapping("authenticate")
	public ResponseEntity<Object> authenticate(@RequestBody User request) throws Exception {
		return service.authenticate(request);
	
	}
}
