package com.shri.ShopNest.service;

import com.shri.ShopNest.exception.exceptions.InvalidUserNameOrPasswordException;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.pojo.SuccessfulLoginResponse;
import com.shri.ShopNest.repo.UserRepo;
import com.shri.ShopNest.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
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

    public SuccessfulLoginResponse verify(User user) throws InvalidUserNameOrPasswordException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            User userDetails = userRepo.findByUsername(((UserDetails)authentication.getPrincipal()).getUsername());
            String token = jwtService.generateToken(user.getUsername());
            return new SuccessfulLoginResponse(userDetails, token);
        }

        throw new InvalidUserNameOrPasswordException("Invalid username or password");
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }
}
