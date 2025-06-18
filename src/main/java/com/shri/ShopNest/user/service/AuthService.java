package com.shri.ShopNest.user.service;

import com.shri.ShopNest.exception.exceptions.InvalidUserNameOrPasswordException;
import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import com.shri.ShopNest.user.repo.UserRepo;
import com.shri.ShopNest.security.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authManager, JwtService jwtService,
                       UserService userService, UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticatedUserResponse verify(User user, HttpServletResponse response) throws InvalidUserNameOrPasswordException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            User userDetails = userRepo.findByUsername(((UserDetails)authentication.getPrincipal()).getUsername());
            String token = jwtService.generateAccessToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            return new AuthenticatedUserResponse(userDetails, token);
        }

        throw new InvalidUserNameOrPasswordException("Invalid username or password");
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }

    public AuthenticatedUserResponse refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("refreshToken".equals(c.getName())) {
                    refreshToken = c.getValue();
                }
            }
        }

        if (refreshToken == null || !jwtService.isValidRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String username = jwtService.extractUserName(refreshToken);
        User user = userRepo.findByUsername(username);

        String newAccessToken = jwtService.generateAccessToken(user.getUsername());

        return new AuthenticatedUserResponse(user, newAccessToken);
    }
}
