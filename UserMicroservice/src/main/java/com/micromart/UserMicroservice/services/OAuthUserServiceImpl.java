package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.LoginResponseDto;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserServiceImpl implements OAuthUserService {
    public final UserService userService;
    private final JWTService jwtService;
    @Override
    public LoginResponseDto handleGoogleOauth(Map<String, Object> parameters) {

        /*{at_hash=0DiFSVdLe6GNHW5Uc9st6w, sub=110155342114676959482,
         email_verified=true, iss=https://accounts.google.com,
         given_name=Shubhangam,
         nonce=f9G5iOEJDudrfeDMjmYuK967_Gb8uB6_QiwgB-tF-zo,
         picture=https://lh3.googleusercontent.com/a/ACg8ocKWZczcYOkUBNvK4QpvNOZekDqtjLBiUtdd3N1A9VtEVF0PlcY=s96-c,
         aud=[925572665700-e5s9h4tmnsivveg4opn4s106h0cuos0g.apps.googleusercontent.com],
         azp=925572665700-e5s9h4tmnsivveg4opn4s106h0cuos0g.apps.googleusercontent.com,
         name=Shubhangam tyagi, exp=2025-02-26T00:39:52Z,
         family_name=tyagi, iat=2025-02-25T23:39:52Z,
         email=tyagishubhangam0090@gmail.com}*/
        String email = (String) parameters.get("email");
        User user = userService.getUserByEmail(email);
        if(user == null) {

            log.info("User with email " + email + " doing signup");

            user.setEmail(email);
            user.setFirstName(parameters.get("given_name").toString());
            user.setLastName(parameters.get("family_name").toString());
            user.setUsername(parameters.get("family_name").toString().toLowerCase() + (int)(Math.random()*10000));
            user.setProvider(parameters.get("iss").toString());
            user.setProfilePicUrl(parameters.get("picture").toString());
            user = userService.registerUser(user);

        }

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserId(user.getId());
        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setImage(user.getProfilePicUrl());
        loginResponseDto.setMessage("Successfully logged in");
        loginResponseDto.setAccessToken(jwtService.generateToken(email,user.getId().toString()));
        return loginResponseDto;
    }
}
