package io.moun.api.security.service;

import io.moun.api.member.domain.Member;
import io.moun.api.security.controller.dto.CheckRequest;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.controller.dto.LoginResponse;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.vo.JwtToken;

public interface AuthService {
    void save(RegisterRequest registerRequest, Member member);

    LoginResponse login(LoginRequest loginRequest);

    void check(CheckRequest checkRequest);

    Auth findAuthByUsername(String username);
}
