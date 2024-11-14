package io.moun.api.security.controller;

import io.moun.api.security.controller.dto.CheckRequest;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.security.controller.dto.LoginResponse;
import io.moun.api.security.controller.dto.RegisterRequest;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String Hello() {
        return "Hello World";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        boolean success = authService.registerAuth(registerRequest);
        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is taken!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtToken jwtToken = authService.loginAuth(loginRequest);
        if (jwtToken != null) {
            return ResponseEntity.ok(new LoginResponse(jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@Valid @RequestBody CheckRequest checkRequest) {
        boolean isValid = authService.checkAuth(checkRequest);
        if (isValid) {
            return ResponseEntity.ok("Valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid");

        }

    }
}
