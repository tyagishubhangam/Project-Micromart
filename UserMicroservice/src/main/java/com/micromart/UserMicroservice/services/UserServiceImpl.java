package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.repositories.UserRepo;
import com.micromart.UserMicroservice.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public void registerUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : null;
    }

    @Override
    public boolean deleteUser(Long id) {
         if(userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
            return true;
        }
         return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
