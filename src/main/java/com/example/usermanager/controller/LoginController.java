package com.example.usermanager.controller;

import com.example.usermanager.entity.Login;
import com.example.usermanager.entity.User;
import com.example.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/user/login")
    public ResponseEntity authenticationUser(@RequestBody Login login){
        List<String>userEmail=userService.checkUserEmail(login.getEmail());
        if (userEmail.isEmpty()||userEmail==null){
            return new ResponseEntity<>("Email does not exist", HttpStatus.NOT_FOUND);
        }
        String hashed_password=userService.checkUserPasswordByEmail(login.getEmail());
        if (!BCrypt.checkpw(login.getPassword(),hashed_password)){
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
        User user=userService.getUserDetailsByEmail(login.getEmail());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
