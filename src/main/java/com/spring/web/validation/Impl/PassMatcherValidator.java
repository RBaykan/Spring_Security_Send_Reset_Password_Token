package com.spring.web.validation.Impl;

import com.spring.web.dto.CreateUser;
import com.spring.web.validation.PasswordMatches;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassMatcherValidator implements ConstraintValidator<PasswordMatches, CreateUser> {

	@Override
	public boolean isValid(CreateUser value, ConstraintValidatorContext context) {
		
		return value.getPassword().equals(value.getRepassword());
	}

	

}
