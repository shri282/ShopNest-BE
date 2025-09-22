package com.shri.ShopNest.modules.user.service;

import com.shri.ShopNest.exception.exceptions.InvalidUserNameOrPasswordException;
import com.shri.ShopNest.modules.user.dto.AuthRequest;
import com.shri.ShopNest.modules.user.dto.RegisterRequest;
import com.shri.ShopNest.modules.user.model.User;
import com.shri.ShopNest.modules.user.repo.UserRepo;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import com.shri.ShopNest.security.service.JwtService;
import jakarta.servlet.http.Cookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authManager, JwtService jwtService,
                       UserService userService, UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticatedUserResponse login(AuthRequest request) throws InvalidUserNameOrPasswordException {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (!auth.isAuthenticated()) {
            throw new InvalidUserNameOrPasswordException("Invalid username or password");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        User user = userRepo.findByUsername(userDetails.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        return new AuthenticatedUserResponse(user, accessToken, refreshToken);
    }

    public User register(RegisterRequest req) {
        User user = req.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }

    public AuthenticatedUserResponse refreshToken(Cookie token) {
        String username = jwtService.extractUserName(token.getValue());

        User user = userRepo.findByUsername(username);
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        return new AuthenticatedUserResponse(user, accessToken, refreshToken);
    }
}
