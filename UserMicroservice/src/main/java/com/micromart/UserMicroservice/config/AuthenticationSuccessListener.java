package com.micromart.UserMicroservice.config;

import com.micromart.UserMicroservice.repositories.UserRepo;
import com.micromart.UserMicroservice.userjwt.JWTService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    public JWTService jwtService;
    public UserRepo userRepo;
    public AuthenticationSuccessListener(JWTService jwtService, UserRepo userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;

    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // Log or perform actions upon successful authentication
        String username = event.getAuthentication().getName();
        System.out.println("Login successful for user: " + username);
        String jwtToken = jwtService.generateToken(username);
        System.out.println("JWT token: " + jwtToken);
        // You can also add more logic here, like logging to a database, etc.
    }
}