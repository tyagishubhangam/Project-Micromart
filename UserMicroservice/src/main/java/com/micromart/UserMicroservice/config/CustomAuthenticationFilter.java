package com.micromart.UserMicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micromart.UserMicroservice.dtos.LoginRequest;
import com.micromart.UserMicroservice.dtos.LoginResponseDto;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.userjwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final  UserService userService;
    private final JWTService jwtService;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.setFilterProcessesUrl("/api/micromart/user/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
       try{
           LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        System.out.println("Attempting to authenticate user: " + username);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain chain, Authentication authResult) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        Long userId = userService.getUserByUsername(authResult.getName()).getId();
        String accessToken = jwtService.generateToken(authResult.getName());
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("Successfully logged in");
        loginResponseDto.setUserId(userId);
        loginResponseDto.setAccessToken(accessToken);
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        failed.printStackTrace();
        loginResponseDto.setMessage(failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponseDto));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
