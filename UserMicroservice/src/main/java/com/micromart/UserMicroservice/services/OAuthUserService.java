package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.LoginResponseDto;

import java.util.Map;
import java.util.Objects;

public interface OAuthUserService {
    public LoginResponseDto handleGoogleOauth(Map<String, Object> parameters);
}
