package com.micromart.UserMicroservice.controllers;

import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/micromart/user")
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam long id) {
        if (userService.getUser(id) != null) {
            return ResponseEntity.ok(userService.getUser(id));
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam long id) {
        if(userService.deleteUser(id)) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @GetMapping("/greet")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello World");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }
}
