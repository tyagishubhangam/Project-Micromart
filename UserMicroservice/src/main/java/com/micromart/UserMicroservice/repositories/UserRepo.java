package com.micromart.UserMicroservice.repositories;

import com.micromart.UserMicroservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    public User findByUsername(String username);
    public User findByEmail(String email);

}
