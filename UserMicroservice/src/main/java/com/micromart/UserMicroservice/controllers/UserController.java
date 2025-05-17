package com.micromart.UserMicroservice.controllers;

import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.mappers.SignupRequestMapper;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.services.cloudinary.CloudinaryService;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/micromart/user")
//@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;
    private final SignupRequestMapper signupRequestMapper;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestPart SignupRequest signupRequest, @RequestPart("avatar") MultipartFile file) {
        try{
            String imageUrl = cloudinaryService.upload(file);
            User user = signupRequestMapper.mapToUser(signupRequest);
            user.setProfilePicUrl(imageUrl);
            userService.registerUser(user);
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
//        System.out.println(request.getHeader("Authorization").substring(7));
        String accessToken = request.getHeader("Authorization").substring(7);
        Long id = Long.parseLong(jwtService.extractUserId(accessToken));
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
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User user) {
//        String token = jwtService.generateToken(user.getUsername());
//        return ResponseEntity.ok(token);
//    }
}
