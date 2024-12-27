package com.spring.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.web.model.Token;
import com.spring.web.model.User;

@Service
public interface TokenService {
    List<Token> allTokens(User user);
    Token getToken(String token);
    
    Token createVerificationToken(User user, String token);
    Token createResetPassowordToken(User user, String token);
    
    
    Boolean verificationToken(Token token);
    

}