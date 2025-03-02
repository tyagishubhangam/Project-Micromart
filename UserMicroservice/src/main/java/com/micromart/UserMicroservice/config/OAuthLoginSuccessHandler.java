package com.micromart.UserMicroservice.config;

import com.micromart.UserMicroservice.dtos.LoginResponseDto;
import com.micromart.UserMicroservice.services.OAuthUserService;
import com.micromart.UserMicroservice.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuthUserService oAuthUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OidcUser user = (OidcUser) authentication.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();
        LoginResponseDto loginResponse = oAuthUserService.handleGoogleOauth(attributes);
//        System.out.println(user.getAttributes().toString());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{message:'" + loginResponse.getMessage() + "'\n accessToken:'"+loginResponse.getAccessToken()+"'\n userId:'"+loginResponse.getUserId()+"'}");
    }
}
