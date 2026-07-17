package com.drivelite.service;

import com.drivelite.dto.LoginResponse;
import com.drivelite.model.User;
import com.drivelite.repository.UserRepository;
import com.drivelite.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }




    public LoginResponse login(
            String email,
            String password
    ){


        Optional<User> userOptional =
                userRepository.findByEmail(email);



        if(userOptional.isEmpty()){

            throw new RuntimeException("User not found");

        }



        User user = userOptional.get();



        if(!passwordEncoder.matches(
                password,
                user.getPassword()
        )){

            throw new RuntimeException("Invalid password");

        }



        String token =
                jwtService.generateToken(email);



        return new LoginResponse(

                token,

                user.getEmail(),

                user.getName(),

                user.getRole()

        );

    }

}