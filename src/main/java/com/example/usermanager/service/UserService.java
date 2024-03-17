package com.example.usermanager.service;

import com.example.usermanager.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    public int registerNewUserServiceMethod(String fName, String lName, String email, String password, Date created_at);
    public List<String>checkUserEmail(String email);
    public String checkUserPasswordByEmail(String email);
    public User getUserDetailsByEmail(String email);
}
