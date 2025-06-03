package com.micromart.UserMicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micromart.UserMicroservice.dtos.LoginResponseDto;
import com.micromart.UserMicroservice.services.OAuthUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${frontend.url}")
    private String frontendUrl;

    private final OAuthUserService oAuthUserService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Can be injected if needed

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OidcUser user = (OidcUser) authentication.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();
        System.out.println("attributes->" + attributes);
        LoginResponseDto loginResponse = oAuthUserService.handleGoogleOauth(attributes);

        if(frontendUrl != null) {
            response.sendRedirect(frontendUrl+"/oauth-success?token="+loginResponse.getAccessToken());
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", loginResponse.getMessage());
        responseBody.put("accessToken", loginResponse.getAccessToken());
        responseBody.put("userId", loginResponse.getUserId());
        responseBody.put("email", loginResponse.getEmail());
        responseBody.put("avatar", loginResponse.getImage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));

        log.info("Google OAuth login successful for user: {}", attributes.get("email"));
        clearAuthenticationAttributes(request);
    }
}
