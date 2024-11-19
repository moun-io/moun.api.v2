package io.moun.api.security.service;

import io.jsonwebtoken.Claims;
import io.moun.api.security.domain.vo.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJwtTokenHelper {
    public JwtToken getJwtToken();

    public void generateToken(Authentication authentication,Long memberId);

    public String getUsername();

    public boolean isValidToken();

    public boolean isValidToken(JwtToken jwtTokenArg);

    public Claims getClaims();

    public Long getMemberId();
}
