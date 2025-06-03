package com.micromart.UserMicroservice.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "myUser")
@Data
public class User {
    @Id
    private String id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true)
    private String phone;
    private String address;
    private String provider;
    private String profilePicUrl;
    private String imagePublicId;


}
