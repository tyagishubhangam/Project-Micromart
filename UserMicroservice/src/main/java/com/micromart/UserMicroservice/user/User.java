package com.micromart.UserMicroservice.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "myUser")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String provider;
    private String profilePicUrl;


}
