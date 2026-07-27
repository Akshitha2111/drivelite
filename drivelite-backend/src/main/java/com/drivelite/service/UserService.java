package com.drivelite.service;

import com.drivelite.dto.ProfileResponse;
import com.drivelite.dto.UpdateProfileRequest;
import com.drivelite.model.User;
import com.drivelite.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get logged-in user's profile
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new ProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    // Update logged-in user's profile
    public ProfileResponse updateProfile(String email, UpdateProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return new ProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}