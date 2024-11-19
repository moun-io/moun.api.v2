package io.moun.api.security.controller;

import io.moun.api.security.controller.dto.CheckRequest;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.security.controller.dto.LoginResponse;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class
AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);

    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@Valid @RequestBody CheckRequest checkRequest) {
        authService.check(checkRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Valid");
    }
}
