package com.spring.web.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="Account")
public class User {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id; 
	
	private String firstname;
	private String lastname;
	@Column(unique = true) private String username;
	private String password;
	@Column(unique = true) private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Token> tokens;
	
	
	private boolean enabled;
	
	
	public User() {
		this.enabled = false;
	}
	
}
