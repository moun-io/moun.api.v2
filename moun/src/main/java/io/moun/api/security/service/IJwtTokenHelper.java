package io.moun.api.security.service;

import io.jsonwebtoken.Claims;
import io.moun.api.security.domain.vo.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJwtTokenHelper {
    JwtToken getJwtToken();

    void generateToken(Authentication authentication,Long memberId);

    String getUsername();

    boolean isValidToken();

    boolean isValidToken(JwtToken jwtTokenArg);

    Claims getClaims();
    void setJwtToken(JwtToken token);

    Long getMemberId();
}
