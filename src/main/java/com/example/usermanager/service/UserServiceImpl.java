package com.example.usermanager.service;

import com.example.usermanager.entity.User;
import com.example.usermanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public int registerNewUserServiceMethod(String fName, String lName, String email, String password, Date created_at) {
        return userRepository.registerNewUser(fName, lName, email, password,created_at);
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
}
