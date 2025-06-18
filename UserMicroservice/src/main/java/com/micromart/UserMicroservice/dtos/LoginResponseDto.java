package com.micromart.UserMicroservice.dtos;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String message;
    private String userId;
    private String email;
    private String image;
    private String accessToken;

}
