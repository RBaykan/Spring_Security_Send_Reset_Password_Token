package com.spring.web.dto;

import com.spring.web.validation.PasswordMatches;
import com.spring.web.validation.PasswordValid;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class ResetPassword {

	private String oldPassword;
	@NotBlank @PasswordValid private String password;
	@NotBlank @PasswordValid private String repassword;
}
