package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.UserProfileDto;
import com.micromart.UserMicroservice.dtos.UserUpdateRequest;
import com.micromart.UserMicroservice.user.User;

import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public List<User> getAllUsers();
    public UserProfileDto getUser(String id);
    public boolean deleteUser(String id);
    public User getUserEntity(String id);
    public User getUserByEmail(String email);

    public User updateUser(String userId, UserUpdateRequest userUpdateRequest);
}
