package com.shri.ShopNest.controller;

import com.shri.ShopNest.model.User;
import com.shri.ShopNest.pojo.SuccessfulLoginResponse;
import com.shri.ShopNest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<SuccessfulLoginResponse> login(@RequestBody User user) throws Exception {
        return new ResponseEntity<>(authService.verify(user), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

}
