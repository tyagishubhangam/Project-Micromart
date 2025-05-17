package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.mappers.SignupRequestMapper;
import com.micromart.UserMicroservice.repositories.UserRepo;
import com.micromart.UserMicroservice.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final SignupRequestMapper signupRequestMapper;
    @Override
    public User registerUser(User user) {
        try{
            //        User user = signupRequestMapper.mapToUser(signupRequest);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            if(user.getPassword() != null) {
                user.setPassword(encoder.encode(user.getPassword()));}
            user.setProvider("LOCAL");
//        System.out.println(user.getPassword());
            return userRepo.save(user);
        }catch(DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw e;

        }


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
