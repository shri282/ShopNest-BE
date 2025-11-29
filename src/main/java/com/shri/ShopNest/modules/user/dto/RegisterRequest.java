package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.enums.Gender;
import com.shri.ShopNest.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class RegisterRequest {
    @NotBlank(message = "username should not be blank")
    String username;

    @Email(message = "email is not valid")
    String email;

    @NotBlank(message = "password should not be blank")
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
