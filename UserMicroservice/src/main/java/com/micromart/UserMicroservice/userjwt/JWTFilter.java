package com.micromart.UserMicroservice.userjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micromart.UserMicroservice.services.PrincipalUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ApplicationContext applicationContext;
    public JWTFilter(JWTService jwtService, ApplicationContext applicationContext) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bypass JWT validation for signup and greet endpoints
        String[] permittedUris = {
                "/api/micromart/user/signup",
                "/api/micromart/user/greet",
                "/api/micromart/user/login",
                "/h2-console",
                "/swagger-ui",
                "/swagger-resources/**",
                "/v3/api-docs",
                "/api/auth/google",
                "/oauth2"
        };
       for (String uri : permittedUris) {
           if (request.getRequestURI().startsWith(uri)) {
               filterChain.doFilter(request, response);
               return;
           }
       }
       try{
            String authHeader = request.getHeader("Authorization");
            String jwtToken = null;
            String username = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                username = jwtService.extractEmail(jwtToken);
            }

            if(username != null) {
                UserDetails userDetails = applicationContext.getBean(PrincipalUserService.class).loadUserByUsername(username);
                if(SecurityContextHolder.getContext().getAuthentication() == null) {
                    if(jwtService.validateToken(jwtToken, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
       }catch(Exception e){

           log.error("JWT processing error: {}", e.getMessage());

           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           response.setContentType("application/json");

           Map<String, Object> errorResponse = new HashMap<>();
           errorResponse.put("status", HttpServletResponse.SC_FORBIDDEN);
           errorResponse.put("error", "Forbidden");
           errorResponse.put("message", e.getMessage());
           errorResponse.put("timestamp", new Date());

           ObjectMapper mapper = new ObjectMapper();
           response.getWriter().write(mapper.writeValueAsString(errorResponse));
           return;
       }

    }

}
