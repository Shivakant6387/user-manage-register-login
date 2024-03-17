package com.example.usermanager.controller;

import com.example.usermanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/test")
    public String testEndPoint(){
        return "Test End point is working";
    }
    @PostMapping("/user/registers")
    public ResponseEntity<?> registerNewUser(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields.");
        }

        // Validate email format
        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        // Validate password strength
        if (!isValidPassword(password)) {
            return ResponseEntity.badRequest().body("Password is weak.");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Date created_at=new Date();
        try {
            int result = userService.registerNewUserServiceMethod(firstName, lastName, email, hashedPassword, created_at);
            if (result != 1) {
                return ResponseEntity.badRequest().body("Failed to register user.");
            }
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering user.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        int minLength = 8;
        return password.length() >= minLength;
    }
}
