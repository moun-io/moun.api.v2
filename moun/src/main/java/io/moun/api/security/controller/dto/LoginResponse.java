package io.moun.api.security.controller.dto;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.security.domain.vo.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private JwtToken jwtToken;
    private MemberResponse member;
}
