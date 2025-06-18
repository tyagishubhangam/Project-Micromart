package com.micromart.UserMicroservice.dtos;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String avatarUrl;
    private String imagePublicId;

}
