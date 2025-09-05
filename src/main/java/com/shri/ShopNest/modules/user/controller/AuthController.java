package com.shri.ShopNest.modules.user.controller;

import com.shri.ShopNest.modules.user.model.User;
import com.shri.ShopNest.modules.user.service.AuthService;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticatedUserResponse> login(@RequestBody User user, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(authService.verify(user, response));
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<AuthenticatedUserResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
