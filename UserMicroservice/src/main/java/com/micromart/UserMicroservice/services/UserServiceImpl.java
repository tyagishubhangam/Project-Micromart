package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.UserProfileDto;
import com.micromart.UserMicroservice.dtos.UserUpdateRequest;
import com.micromart.UserMicroservice.dtos.mappers.SignupRequestMapper;
import com.micromart.UserMicroservice.dtos.mappers.UserMapper;
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
    private final UserMapper userMapper;

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
    public UserProfileDto getUser(String id) {
        User userEntity =  userRepo.findById(id).isPresent() ? userRepo.findById(id).get() :  null;
        if(userEntity == null) {
            return null;
        }
        UserProfileDto userProfileDto = userMapper.toUserProfileDto(userEntity);
        return userProfileDto;
    }

    @Override
    public boolean deleteUser(String id) {
         if(userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
            return true;
        }
         return false;
    }

    @Override
    public User getUserEntity(String id) {
        return userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : null;
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepo.findByEmail(email);
    }

    @Override
    public User updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        if(userUpdateRequest.getFirstName() != null) {
            existingUser.setFirstName(userUpdateRequest.getFirstName());
        }
        if(userUpdateRequest.getLastName() != null) {
            existingUser.setLastName(userUpdateRequest.getLastName());
        }
        if (userUpdateRequest.getAddress() != null) {
            existingUser.setAddress(userUpdateRequest.getAddress());
        }
        if (userUpdateRequest.getPhoneNumber() != null) {
            existingUser.setPhone(userUpdateRequest.getPhoneNumber());
        }
        if(userUpdateRequest.getAvatarUrl() != null) {
            existingUser.setProfilePicUrl(userUpdateRequest.getAvatarUrl());
        }
        if(userUpdateRequest.getImagePublicId() != null) {
            existingUser.setImagePublicId(userUpdateRequest.getImagePublicId());
        }
        return userRepo.save(existingUser);
    }


}
