package com.micromart.UserMicroservice.userjwt;

import com.micromart.UserMicroservice.services.PrincipalUserService;
import com.micromart.UserMicroservice.user.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
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
        if (request.getRequestURI().equals("/api/micromart/user/signup") || request.getRequestURI().equals("/api/micromart/user/greet") || request.getRequestURI().equals("/api/micromart/user/login")) {
            filterChain.doFilter(request, response);
            return; // Skip further processing
        }

        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUsername(jwtToken);
        }
        UserDetails userDetails = applicationContext.getBean(PrincipalUserService.class).loadUserByUsername(username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtService.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
