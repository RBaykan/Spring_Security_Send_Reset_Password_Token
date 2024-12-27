package com.spring.web.event;


import org.springframework.context.ApplicationEvent;

import com.spring.web.model.User;

import lombok.Data;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private String appUrl;
	
	private User user;
	
	public OnRegistrationCompleteEvent(User user, String  appUrl) {
		super(user);
		
		this.user = user;
		this.appUrl = appUrl;
		
		
	}

}
