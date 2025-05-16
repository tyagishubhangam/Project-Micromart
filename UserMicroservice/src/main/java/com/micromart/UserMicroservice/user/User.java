package com.micromart.UserMicroservice.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "myUser")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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


}
