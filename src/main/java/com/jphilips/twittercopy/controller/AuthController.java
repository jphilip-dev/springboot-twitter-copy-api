package com.jphilips.twittercopy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.twittercopy.dto.JwtResponseDTO;
import com.jphilips.twittercopy.dto.LoginRequestDTO;
import com.jphilips.twittercopy.dto.MyUserRequestDTO;
import com.jphilips.twittercopy.service.MyUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Valid
public class AuthController {
	
	private MyUserService service;
	
	public AuthController(MyUserService service) {
		this.service = service;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
		
		JwtResponseDTO jwt = new JwtResponseDTO("1234"); // placeholder
		
		return new ResponseEntity<>(jwt,HttpStatus.ACCEPTED);
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody MyUserRequestDTO myUserRequestDTO ){
		
		
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
