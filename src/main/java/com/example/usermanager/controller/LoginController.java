package com.example.usermanager.controller;

import com.example.usermanager.entity.Login;
import com.example.usermanager.entity.User;
import com.example.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/user/update/email")
    public ResponseEntity<?> updateUserEmail(@RequestParam("oldEmail") String oldEmail,
                                             @RequestParam("newEmail") String newEmail) {
        if (!userService.isUserExistsByEmail(oldEmail)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (userService.isUserExistsByEmail(newEmail)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New email already exists");
        }

        userService.updateUserEmail(oldEmail, newEmail);
        return ResponseEntity.ok("Email updated successfully");
    }

    @PutMapping("/user/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestParam("email") String email,
                                                @RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }

        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        userService.updateUserPassword(email, hashedNewPassword);
        return ResponseEntity.ok("Password updated successfully");
    }
}
