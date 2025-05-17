package com.micromart.UserMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String profilePicUrl;
}



//{
//        "id": 12,
//        "username": "tyagi189",
//        "password": "$2a$12$Qpgk9UXPsSDrs4qCR0OryOfgNzJTgREyQ3QlJeLI4p.OX1BoIZKFW",
//        "firstName": "Shubhangam",
//        "lastName": "Tyagi",
//        "email": "shubhangamtyagi0090@gmail.com",
//        "phone": "7838679146",
//        "address": "362, Tyagi Market Jagrati vihar",
//        "provider": "LOCAL",
//        "profilePicUrl": "https://res.cloudinary.com/dlotcoc2a/image/upload/v1747194596/user-avatars/r16ejk1ssu3yfhjzsiqe.jpg"
//        }
