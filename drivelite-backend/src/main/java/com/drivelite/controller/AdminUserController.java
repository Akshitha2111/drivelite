package com.drivelite.controller;


import com.drivelite.model.User;
import com.drivelite.repository.UserRepository;

import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/admin/users")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminUserController {



    private final UserRepository userRepository;



    public AdminUserController(
            UserRepository userRepository
    ){

        this.userRepository=userRepository;

    }





    // Get all users
    @GetMapping
    public List<User> getUsers(){


        return userRepository.findAll();

    }





    // Change role
    @PutMapping("/{id}/role")
    public User updateRole(
            @PathVariable Long id,
            @RequestParam String role
    ){


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow();



        user.setRole(role);



        return userRepository.save(user);

    }





    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id
    ){


        userRepository.deleteById(id);


        return "User deleted successfully";

    }


}