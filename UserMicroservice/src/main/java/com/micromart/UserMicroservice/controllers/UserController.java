package com.micromart.UserMicroservice.controllers;

import com.micromart.UserMicroservice.dtos.ResponseDto;
import com.micromart.UserMicroservice.dtos.SignupRequest;
import com.micromart.UserMicroservice.dtos.UserProfileDto;
import com.micromart.UserMicroservice.dtos.UserUpdateRequest;
import com.micromart.UserMicroservice.dtos.mappers.SignupRequestMapper;
import com.micromart.UserMicroservice.services.UserService;
import com.micromart.UserMicroservice.services.cloudinary.CloudinaryService;
import com.micromart.UserMicroservice.user.User;
import com.micromart.UserMicroservice.userjwt.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
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
            Map<?,?> imageData = cloudinaryService.upload(file);
            String imageUrl = imageData.get("secure_url").toString();
            String publicId = imageData.get("public_id").toString();
            User user = signupRequestMapper.mapToUser(signupRequest);
            user.setProfilePicUrl(imageUrl);
            user.setImagePublicId(publicId);
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
    public ResponseEntity<ResponseDto> getUser(HttpServletRequest request) {
//        System.out.println(request.getHeader("Authorization").substring(7));
        String accessToken = request.getHeader("Authorization").substring(7);
        Long id = Long.parseLong(jwtService.extractUserId(accessToken));
        UserProfileDto userProfileDto = userService.getUser(id);
        if (userProfileDto != null) {
            return ResponseEntity.status(200).body(
                    ResponseDto.builder()
                            .message("Success")
                            .data(userProfileDto)
                            .build()
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseDto.builder()
                        .message("User not found")
                        .build()
        );

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

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> update(HttpServletRequest request, @RequestBody UserUpdateRequest userUpdateRequest) {

        try{
            String accessToken = request.getHeader("Authorization").substring(7);
            Long id = Long.parseLong(jwtService.extractUserId(accessToken));
            User updatedUser = userService.updateUser(id, userUpdateRequest);
            return ResponseEntity.status(200).body(
                    ResponseDto.builder()
                            .message("User updated successfully")
                            .data(updatedUser)
                            .build()
            );
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(400)
                    .body(
                            ResponseDto.builder()
                                    .message("Duplicate entries not allowed")
                                    .build()
                    );
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(
                    ResponseDto.builder()
                            .message(e.getMessage())
                            .build()
            );
        }

    }

    @PatchMapping(value = "/update/avatar",consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDto> updateAvatar(HttpServletRequest  request, @RequestPart("avatar") MultipartFile file) {
        try{
//            System.out.println("updating avatar");
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = Long.parseLong(jwtService.extractUserId(accessToken));
            Map<?,?> imageData = cloudinaryService.upload(file);
            String imageUrl = imageData.get("secure_url").toString();
            String publicId = imageData.get("public_id").toString();
            User user = userService.getUserEntity(userId);
           String oldPublicId = user.getImagePublicId();
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            userUpdateRequest.setAvatarUrl(imageUrl);
            userUpdateRequest.setImagePublicId(publicId);
            userService.updateUser(userId, userUpdateRequest);
            cloudinaryService.deleteImage(oldPublicId);
            return ResponseEntity.status(200).body(
                    ResponseDto.builder()
                            .message("User avatar updated successfully")
                            .data(imageUrl)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(
                    ResponseDto.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

}
