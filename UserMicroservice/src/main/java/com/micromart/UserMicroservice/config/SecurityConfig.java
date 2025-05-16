package com.micromart.UserMicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micromart.UserMicroservice.services.PrincipalUserService;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTFilter;
import com.micromart.UserMicroservice.userjwt.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig  {
    @Value("${frontend.url}")
    private String frontendUrl;

    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
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
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers-> headers.frameOptions(f -> f.sameOrigin()));
        http.authorizeHttpRequests(request -> request.requestMatchers(
                                       "api/micromart/user/signup",
                                        "api/micromart/user/greet",
                                        "/h2-console/**",
                                        "api/micromart/user/login",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/api/auth/google",
                                        "/oauth2/**"
                                            )
                        .permitAll()
                        .anyRequest().authenticated()
        )

                .oauth2Login(
                        oauth -> oauth

                                .authorizationEndpoint(authEndpoint ->
                                        authEndpoint.baseUri("/oauth2/authorize"))
                                .redirectionEndpoint(authEndpoint ->
                                        authEndpoint.baseUri("/oauth2/callback"))
                                .successHandler(oAuthLoginSuccessHandler)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);




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


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList(frontendUrl));
//        configuration.setAllowedMethods(Arrays.asList( "POST", "GET","PUT","DELETE","PATCH","OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow necessary headers
//		configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
