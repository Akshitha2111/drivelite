package com.drivelite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello() {

        return "Spring Boot is working!";

    }

}