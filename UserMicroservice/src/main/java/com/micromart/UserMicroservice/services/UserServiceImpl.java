package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.mappers.SignupRequestMapper;
import com.micromart.UserMicroservice.repositories.UserRepo;
import com.micromart.UserMicroservice.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final SignupRequestMapper signupRequestMapper;
    @Override
    public void registerUser(User user) {

//        User user = signupRequestMapper.mapToUser(signupRequest);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if(user.getPassword() != null) {
        user.setPassword(encoder.encode(user.getPassword()));}
        user.setProvider("LOCAL");
//        System.out.println(user.getPassword());
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

    @Override
    public User getUserByEmail(String email) {

        return userRepo.findByEmail(email);
    }
}
