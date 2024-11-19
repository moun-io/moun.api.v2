package io.moun.api.security.controller.dto;

import io.moun.api.security.domain.vo.JwtToken;
import lombok.Data;

@Data
public class LoginResponse {
    private JwtToken jwtToken;
    public LoginResponse(JwtToken jwtToken) {
        this.jwtToken=jwtToken;
    }
}
