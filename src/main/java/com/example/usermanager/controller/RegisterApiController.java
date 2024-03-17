package com.example.usermanager.controller;

import com.example.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class RegisterApiController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity registerNewUser(
            @RequestParam("first_name") String first_name,
            @RequestParam("last_name") String last_name,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Please complete all Fields", HttpStatus.BAD_REQUEST);
        }
        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());
        Date created_at=new Date();
        int result = userService.registerNewUserServiceMethod(first_name, last_name, email, hashed_password,created_at);
        if (result != 1) {
            return new ResponseEntity<>("Failed to Register User", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User Register Successfully!", HttpStatus.OK);
    }
}
