package com.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
	
	@NotBlank private String username;
	@NotBlank private String password;
	
}
