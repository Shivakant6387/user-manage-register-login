package com.example.usermanager.service;

import com.example.usermanager.entity.User;
import com.example.usermanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public int registerNewUserServiceMethod(String fName, String lName, String email, String password, Date created_at) {
        return userRepository.registerNewUser(fName, lName, email, password, created_at);
    }

    @Override
    public List<String> checkUserEmail(String email) {
        return userRepository.checkUserEmail(email);
    }

    @Override
    public String checkUserPasswordByEmail(String email) {
        return userRepository.checkPasswordByEmail(email);
    }

    @Override
    public User getUserDetailsByEmail(String email) {
        return userRepository.getUserDetailsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserDetailsByEmail(email);
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        return userRepository.checkUserEmail(email).size() > 0;
    }

    @Override
    public void updateUserEmail(String oldEmail, String newEmail) {
        Date updated_at = new Date();
        userRepository.updateUserEmail(oldEmail, newEmail, updated_at);
    }

    @Override
    public void updateUserPassword(String email, String hashedNewPassword) {
        Date updated_at = new Date();
        userRepository.updateUserPassword(email, hashedNewPassword, updated_at);
    }

    @Override
    public void deleteUserByEmailAndPassword(String email, String password) {
        User user = userRepository.getUserDetailsByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
