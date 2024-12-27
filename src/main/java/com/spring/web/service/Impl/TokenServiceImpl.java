package com.spring.web.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.web.exceptions.TokenNotFound;
import com.spring.web.exceptions.UserNotFound;
import com.spring.web.model.ResetPasswordToken;
import com.spring.web.model.Token;
import com.spring.web.model.User;
import com.spring.web.model.VerificationToken;
import com.spring.web.repository.TokenRepository;
import com.spring.web.service.TokenService;


@Service
public class TokenServiceImpl implements TokenService {

	private TokenRepository repository;
	
	
	public TokenServiceImpl(TokenRepository repository) {
		this.repository = repository;
	
		
	}
	
	@Override
	public List<Token> allTokens(User user) {
		List<Token> tokens = new ArrayList<Token>();
		try {
			
			 tokens = repository.findByUser(user);
		}catch(Exception e) {
			
			if(e.getMessage().contains("user")) {
				throw new UserNotFound();
			}
		}
		return tokens;
	}

	@Override
	public Token getToken(String token) {
		
		return repository.findByToken(token).orElseThrow(() -> new TokenNotFound());
	}


	@Override
	public Boolean verificationToken(Token token) {
		   if (!token.isValid()) {
	            return false;
	        }

	        Calendar cal = Calendar.getInstance();
	        if (token.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
	            token.setValid(false);
	            return false;
	        }

	       
	        return true;
	}

	@Override
	public Token createVerificationToken(User user, String token) {
		Token t = new VerificationToken();
		t.setUser(user);
		t.setToken(token);
		repository.save(t);
		return t;
	}

	@Override
	public Token createResetPassowordToken(User user, String token) {
		Token t = new ResetPasswordToken();
		t.setUser(user);
		t.setToken(token);
		repository.save(t);
		return t;
	}

	
	
	
  /*  private final TokenRepository<T> repository;

    public TokenServiceImpl(TokenRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> allTokens(User user) {
        List<T> tokens = repository.findByUser(user);
        return tokens;
    }

    @Override
    public T getToken(String token) {
        return repository.findByToken(token)
                         .orElseThrow(() -> new TokenNotFound());
    }

    @Override
    public T createToken(User user, String token) {
        T t = createNewToken();
        t.setUser(user);
        t.setToken(token);
        repository.save(t);
        return t;
    }

    private T createNewToken() {
   
        return (T) new Token();  
    }

    @Override
    public Boolean verificationToken(T token) {
        if (!token.isValid()) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        if (token.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
            token.setValid(false);
            repository.save(token);
            return false;
        }

        token.setValid(true);
        repository.save(token);
        return true;
    }
   */
}
