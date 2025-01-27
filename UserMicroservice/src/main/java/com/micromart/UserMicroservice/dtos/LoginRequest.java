package com.micromart.UserMicroservice.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
