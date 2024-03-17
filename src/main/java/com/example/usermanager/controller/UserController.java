package com.example.usermanager.controller;

import com.example.usermanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/test")
    public String testEndPoint(){
        return "Test End point is working";
    }
}
