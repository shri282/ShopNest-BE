package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.enums.Gender;
import com.shri.ShopNest.model.User;

import java.time.LocalDate;

public class RegisterRequest {
    String username;
    String email;
    String password;
    String phNo;
    LocalDate dob;
    Gender gender;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhNo(phNo);
        user.setDob(dob);
        user.setGender(gender);
        return user;
    }
}
