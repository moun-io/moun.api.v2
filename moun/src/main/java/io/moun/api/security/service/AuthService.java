package io.moun.api.security.service;

import io.moun.api.security.controller.dto.CheckRequest;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.vo.JwtToken;

public interface AuthService {
    public void save(RegisterRequest registerRequest);

    public JwtToken login(LoginRequest loginRequest);

    public void check(CheckRequest checkRequest);
    public Auth findAuthByUsername(String username);
}
