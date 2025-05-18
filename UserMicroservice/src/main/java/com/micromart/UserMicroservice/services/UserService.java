package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.UserProfileDto;
import com.micromart.UserMicroservice.dtos.UserUpdateRequest;
import com.micromart.UserMicroservice.user.User;

import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public List<User> getAllUsers();
    public UserProfileDto getUser(Long id);
    public boolean deleteUser(Long id);
    public User getUserEntity(Long id);
    public User getUserByEmail(String email);

    public User updateUser(Long userId, UserUpdateRequest userUpdateRequest);
}
