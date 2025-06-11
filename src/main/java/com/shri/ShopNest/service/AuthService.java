package com.shri.ShopNest.service;

import com.shri.ShopNest.exception.exceptions.InvalidUserNameOrPasswordException;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.pojo.SuccessfulLoginResponse;
import com.shri.ShopNest.repo.UserRepo;
import com.shri.ShopNest.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SuccessfulLoginResponse verify(User user) throws Exception {
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
