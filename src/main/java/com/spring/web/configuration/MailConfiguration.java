package com.spring.web.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
	/*
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("yourhost");
		mailSender.setPort(youtport);
		
		
		mailSender.setUsername("youraccount");
		mailSender.setPassword("yourpassword");
		
		

        return mailSender;
		
		
	}*/

}
