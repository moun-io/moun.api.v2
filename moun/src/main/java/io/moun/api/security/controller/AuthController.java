package io.moun.api.security.controller;

import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.security.controller.dto.LoginResponse;
import io.moun.api.security.infrastructure.JwtTokenHelper;
import io.moun.api.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class
AuthController {
    private final AuthService authService;
    private final JwtTokenHelper jwtTokenHelper;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);

    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        authService.check();
        return ResponseEntity.status(HttpStatus.OK).body("member_id : " + jwtTokenHelper.getMemberId() + " Valid");
    }
}
