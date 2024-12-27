package com.spring.web.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.web.dto.CreateUser;
import com.spring.web.dto.LoginUser;
import com.spring.web.dto.UserDTO;
import com.spring.web.service.UserService;
import com.spring.web.validation.PasswordValid;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/user")
public class UserController {
	
	
	private final UserService service;
	
	public UserController(UserService service){
		this.service = service;
	}

	@GetMapping()
	public List<UserDTO> allUser(){
		return service.getAllUser();
	}
	
	
	@PostMapping()
	public UserDTO register(@RequestBody @Valid CreateUser user) {
		
		
		
		return service.registerNewUser(user);
	}
	
	@GetMapping("registrationConfirm")
	public String sendRegistartionToken(@RequestParam("token") String token) {
		
		
		return service.confirmRegistration(token);
		
	}
	
	@GetMapping("reSendRegistartionToken")
	public String resendRegistartionToken(@RequestParam("email") String email) {
		
		return service.reSendToken(email);
	}
	
	
	@PostMapping("login")
	public String login(@RequestBody @Valid LoginUser user) {
		
		
		
		return service.login(user);
	}
	
	@GetMapping("resetPassword")
	public String resetPassword(@RequestParam("email") String email) {
		
		return service.resetPassword(email);
	}
	
	@PostMapping("resetPasswordToken")
	public String resetPasswordToken(@RequestParam("token") String token, 
										@RequestParam("email") String email, 
										@RequestParam("password") @PasswordValid String password) {
		
		
		
		return service.resetPasswordToken(token, email, password);
		
	}
	
}
