package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.user.User;

import java.util.List;

public interface UserService {
    public void registerUser(User user);
    public List<User> getAllUsers();
    public User getUser(Long id);
    public boolean deleteUser(Long id);
}
