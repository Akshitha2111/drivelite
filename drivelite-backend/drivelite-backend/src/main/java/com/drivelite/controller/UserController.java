package com.drivelite.controller;

import com.drivelite.dto.ProfileResponse;
import com.drivelite.dto.RegisterResponse;
import com.drivelite.dto.UpdateProfileRequest;
import com.drivelite.model.User;
import com.drivelite.repository.UserRepository;
import com.drivelite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register User
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerUser(
            @Valid @RequestBody User user) {

        user.setRole("USER");

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole()
        );
    }

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Get User By ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // Delete User
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);
    }

    // Get Logged-in User Profile
    @GetMapping("/profile")
    public ProfileResponse getProfile(Authentication authentication) {

        return userService.getProfile(authentication.getName());
    }

    // Update Logged-in User Profile
    @PutMapping("/profile")
    public ProfileResponse updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {

        return userService.updateProfile(
                authentication.getName(),
                request
        );
    }
}