package com.spring.web.service.Impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.web.dto.CreateUser;
import com.spring.web.dto.LoginUser;
import com.spring.web.dto.UserDTO;
import com.spring.web.event.OnRegistrationCompleteEvent;
import com.spring.web.event.OnResetPasswordEvent;
import com.spring.web.exceptions.EmailAlReady;
import com.spring.web.exceptions.UserAlReady;
import com.spring.web.exceptions.UserNotFound;
import com.spring.web.mapper.UserMapper;
import com.spring.web.model.ResetPasswordToken;
import com.spring.web.model.Token;
import com.spring.web.model.User;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.TokenService;
import com.spring.web.service.UserService;

import jakarta.transaction.Transactional;



@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	private final ApplicationEventPublisher publisher;
	private final PasswordEncoder encoder;
	public TokenService tokenService;
	
	public UserServiceImpl(UserRepository userRepository, 
			ApplicationEventPublisher publisher, TokenService tokenService,
			PasswordEncoder encoder) {
		
		this.userRepository = userRepository;
		this.publisher = publisher;
		this.encoder = encoder;
		this.tokenService = tokenService;
	}
	
	

	@Override
	public List<UserDTO> getAllUser() {
		
		List<User> users = userRepository.findAll();
		return users.stream().map(
				(user) -> UserMapper.ModeltoDto(user)
				).collect(Collectors.toList());
	}


	@Override
	public UserDTO registerNewUser(CreateUser user){
	
		
		User newUser = new User();
		
		try {
		
		newUser.setEmail(user.getEmail());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setUsername(user.getUsername());
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		
		
		
		userRepository.save(newUser);
		
		
		
		publisher.publishEvent(new OnRegistrationCompleteEvent(newUser, "http://localhost:8080/api/user/registrationConfirm"));
		

		
		
		
		}catch(Exception e){
			
			if(e instanceof DataIntegrityViolationException ) {
				final String errMes = e.getMessage();
				
				if(errMes.contains("user")) {
					System.err.println("user benzerliği");
					throw new UserAlReady();
				}
				

				if(errMes.contains("email")) {
					System.err.println("email benzerliği");
					throw new EmailAlReady();
					
				}
			}
			
			
		}
		
		return UserMapper.ModeltoDto(newUser);
		
	}



	@Override
	@Transactional
	public String confirmRegistration(String token) {
		
		Token t = tokenService.getToken(token);
		if(!tokenService.verificationToken(t)) {
			return "Token is not valid";
		}
		
		t.setValid(false);
		User user = t.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		
		return "Account is enabled";
	}
	
	@Override
	@Transactional
	public String reSendToken(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(
				()-> new UserNotFound());
		if(user.isEnabled()) {
			return "Kullanıcı zaten aktif";
		}
		
		List<Token> oldTokens = tokenService.allTokens(user);
		for(var token : oldTokens) {
			token.setValid(false);
		}
		
		publisher.publishEvent(new OnRegistrationCompleteEvent(user, "http://localhost:8080/api/user/registrationConfirm"));
		
		return "Token was resend";
	}



	@Override
	public String login(LoginUser user) {
		User loginUser = userRepository.findByUsername(user.getUsername())
				.orElseThrow(() -> new UserAlReady());
		
		if(encoder.matches(user.getPassword(), loginUser.getPassword())) {
			return "Giriş başarılı. Girdiğin şire: " + user.getPassword();
		}
		
		return "Giriş başarısız Girdiğin şire:" + user.getPassword();
	}



	@Override
	public String resetPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound());
		publisher.publishEvent(new OnResetPasswordEvent(user, "http://localhost:8080/api/user/resetPasswordToken"));
		
		return "Password Reset link was send";
	}



	@Override
	public String resetPasswordToken(String token, String email, String password) {
		ResetPasswordToken t = (ResetPasswordToken) tokenService.getToken(token);
		if(!tokenService.verificationToken(t)) {
			return "token is not valid";
		}
		
		if(!email.equals(t.getUser().getEmail())) {
			return "email not valid";
		}
		
		t.setValid(false);
		User user = t.getUser();
		user.setPassword(encoder.encode(password));
		
		userRepository.save(user);
		
		return "Password change";
		
		
	}

	
	
	
		
	

}
