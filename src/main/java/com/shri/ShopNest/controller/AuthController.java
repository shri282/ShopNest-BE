package com.shri.ShopNest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shri.ShopNest.modules.auth.AuthService;
import com.shri.ShopNest.modules.user.dto.AuthRequest;
import com.shri.ShopNest.modules.user.dto.RegisterRequest;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import com.shri.ShopNest.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/auth")
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

    @GetMapping("refresh")
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

    @GetMapping("oauth/{provider}/start")
    public void oauthLogin(@PathVariable("provider") String provider,
                                             HttpServletResponse res) throws IOException {
        String state = UUID.randomUUID().toString();

        ResponseCookie stateCookie = ResponseCookie.from("oauth_state", state)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/auth/oauth/github")
                .maxAge(Duration.ofMinutes(10))
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, stateCookie.toString());

        String loginUri = authService.oauthLogin(provider, state);
        res.sendRedirect(loginUri);
    }

    @GetMapping("oauth/{provider}/callback")
    public ResponseEntity<String> oAuthCallback(@RequestParam("code") String code,
                              @PathVariable("provider") String provider,
                              @RequestParam("state") String state,
                              @CookieValue(name = "oauth_state", required = false) String stateCookie,
                              HttpServletResponse response) throws JsonProcessingException {

        if (stateCookie == null || !stateCookie.equals(state)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid state");
        }

        String html = authService.oauthCallback(provider, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }

}
