package com.example.journalapp.Auth.Service;

import com.example.journalapp.Auth.Entity.AuthEntity;
import com.example.journalapp.Auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AuthService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Autowired
    AuthRepository authRepository;
    
    public String login(String username) {
        String token = generateRandomString(50);
        AuthEntity auth = new AuthEntity(token,username);
        authRepository.save(auth);
        return token;
    }

    public String getUserName(HttpHeaders headers) throws Exception {
        String access_token=headers.getFirst("access-token");
        AuthEntity auth= authRepository.findByToken(access_token);
        if(auth==null) {
            throw new Exception("User not exist");
        }
        return auth.getUserName();
    }

    public  boolean authenticateUser(String token) {
       AuthEntity auth= authRepository.findByToken(token);
       return auth != null;
    }




}
