package com.micromart.UserMicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micromart.UserMicroservice.dtos.LoginRequest;
import com.micromart.UserMicroservice.dtos.LoginResponseDto;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
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
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println("Attempting to authenticate user: " + email);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
//        setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }catch (BadCredentialsException e){
           logger.error(e.getMessage());
           throw new BadCredentialsException("Bad credentials");
//           return null;
       }
       catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain chain, Authentication authResult) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        System.out.println("Successfully authenticated user: " + authResult.getName());
        User user = userService.getUserByEmail(authResult.getName());
        Long userId = userService.getUserByEmail(authResult.getName()).getId();
        String accessToken = jwtService.generateToken(authResult.getName());
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("Successfully logged in");
        loginResponseDto.setUserId(userId);
        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setImage(user.getProfilePicUrl());
        loginResponseDto.setAccessToken(accessToken);
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        log.error(failed.getMessage());
        loginResponseDto.setMessage(failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponseDto));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
