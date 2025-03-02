package com.micromart.UserMicroservice.dtos.mappers;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.user.User;
import org.springframework.stereotype.Component;

@Component
public class SignupRequestMapper {

    public User mapToUser(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setProfilePicUrl(signupRequest.getProfilePicUrl());
        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());

        return user;
    }
}
