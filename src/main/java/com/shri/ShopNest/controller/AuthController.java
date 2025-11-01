package com.shri.ShopNest.controller;

import com.shri.ShopNest.modules.user.dto.AuthRequest;
import com.shri.ShopNest.modules.user.dto.RegisterRequest;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.modules.auth.AuthService;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import com.shri.ShopNest.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticatedUserResponse> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthenticatedUserResponse authResponse = authService.login(request);
        CookieUtils.addCookie(
                response,
                "refreshToken",
                authResponse.getRefreshToken(),
                7 * 24 * 60 * 60,
                false,
                true
        );

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<AuthenticatedUserResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie token = CookieUtils.getCookie(request, "refreshToken")
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        AuthenticatedUserResponse authResponse = authService.refreshToken(token);

        CookieUtils.addCookie(
                response,
                "refreshToken",
                authResponse.getRefreshToken(),
                7 * 24 * 60 * 60,
                false,
                true
        );
        return ResponseEntity.ok(authResponse);
    }

    public String oauth(HttpServletRequest req, HttpServletResponse res) {

        return "";
    }

}
