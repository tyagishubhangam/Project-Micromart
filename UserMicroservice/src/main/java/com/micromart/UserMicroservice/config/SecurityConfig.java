package com.micromart.UserMicroservice.config;

import com.micromart.UserMicroservice.services.PrincipalUserService;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.userjwt.JWTFilter;
import com.micromart.UserMicroservice.userjwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private final PrincipalUserService principalUserService;
    private final JWTFilter jwtFilter;
    private final JWTService jwtService;
//    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final UserService userService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),userService, jwtService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/micromart/user/login");
        http.csrf(customizer -> customizer.disable())
                .headers(headers-> headers.frameOptions(f -> f.sameOrigin()));
        http.authorizeHttpRequests(request -> request.requestMatchers("api/micromart/user/signup","api/micromart/user/greet","/h2-console/**","api/micromart/user/login","/swagger-ui/**","/v3/api-docs/**")
        .permitAll().anyRequest().authenticated()
        )


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

        ;


        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authProvider.setUserDetailsService(principalUserService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
