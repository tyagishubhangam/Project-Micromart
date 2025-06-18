package com.micromart.UserMicroservice.dtos.mappers;

import com.micromart.UserMicroservice.dtos.UserProfileDto;
import com.micromart.UserMicroservice.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfileDto toUserProfileDto(User user) {

        return UserProfileDto.builder()
                .userId(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .profilePicUrl(user.getProfilePicUrl())
                .phoneNumber(user.getPhone())
                .build();
    }
}
