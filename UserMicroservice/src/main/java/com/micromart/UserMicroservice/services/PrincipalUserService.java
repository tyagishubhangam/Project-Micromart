package com.micromart.UserMicroservice.services;

import com.micromart.UserMicroservice.repositories.UserRepo;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.user.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalUserService implements UserDetailsService {
    UserService userService;
    public PrincipalUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser =  userService.getUserByUsername(username);

        if (myUser == null) {
            log.error("User not found with username: " + username);
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(myUser);
    }
}
