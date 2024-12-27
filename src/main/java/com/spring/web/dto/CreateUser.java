package com.spring.web.dto;


import com.spring.web.validation.EmailValid;
import com.spring.web.validation.PasswordMatches;
import com.spring.web.validation.PasswordValid;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class CreateUser{

	
	@NotBlank String firstname;
	@NotBlank private String lastname;
	@NotBlank private String username;
	@NotBlank @PasswordValid private String password;
	@NotBlank private String repassword ;
	
	@NotBlank @EmailValid private String email;

}
