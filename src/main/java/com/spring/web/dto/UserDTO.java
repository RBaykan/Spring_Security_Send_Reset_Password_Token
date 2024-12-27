package com.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private boolean enabled;
	
	private String email;
	
	
}
