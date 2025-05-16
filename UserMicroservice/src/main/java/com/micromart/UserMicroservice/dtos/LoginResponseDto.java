package com.micromart.UserMicroservice.dtos;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String message;
    private Long userId;
    private String email;
    private String image;
    private String accessToken;

}
