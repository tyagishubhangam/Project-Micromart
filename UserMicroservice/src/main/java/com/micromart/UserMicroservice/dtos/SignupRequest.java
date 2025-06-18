package com.micromart.UserMicroservice.dtos;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
