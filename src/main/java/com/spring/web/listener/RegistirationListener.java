package com.spring.web.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.spring.web.event.OnRegistrationCompleteEvent;
import com.spring.web.model.User;
import com.spring.web.service.TokenService;


@Component
public class RegistirationListener implements ApplicationListener<OnRegistrationCompleteEvent>{


	private final TokenService tokenService;
	
	private final MessageSource messages;
	 
	private final JavaMailSender sender;
	
	public RegistirationListener(TokenService tokenService,
			MessageSource messages, JavaMailSender sender) {
		
			this.tokenService = tokenService;
			this.messages = messages;
			this.sender = sender;
	}
	
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		
		this.confirmRegistration(event);
	}
	
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(user, token);
		
		String recipientAdress = user.getEmail();
		String subject = "Registration Confirmation";
		
		String confirmationURL = event.getAppUrl() + "?token="+token;
		String message = messages.getMessage("message.RegSucc", null, null);
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAdress);
		email.setSubject(subject);
		email.setText(message + "\r\n" + confirmationURL);
		sender.send(email);
	
	}

}
