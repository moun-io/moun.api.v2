package io.moun.api.security.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.service.IJwtTokenHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JwtTokenHelper implements IJwtTokenHelper {
    private JwtToken jwtToken;
    private static final int JWT_EXPIRATION_PERIOD= 40000000;
    private static final String ENV_JWT_SECRET_KEY = "your-32-characters-or-longer-secret-key";
//            System.getenv("JWT_SECRET_KEY");
    private static final SecretKey JWT_SECRET_KEY = Keys.hmacShaKeyFor(ENV_JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final JwtParser JWT_PARSER = Jwts.parser().verifyWith(JWT_SECRET_KEY).build();


    @Autowired
    public JwtTokenHelper(HttpServletRequest request) {
        // setTokenFromRequest
        String bearerTokenValue = request.getHeader("Authorization");
        if (bearerTokenValue != null && bearerTokenValue.startsWith("Bearer ")) {
            jwtToken = new JwtToken(bearerTokenValue.substring(7));
        }
    }

    public JwtToken getJwtToken() {
        return jwtToken;
    }

    public void generateToken(Authentication authentication) {
        String tokenValue =Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_PERIOD))
                .signWith(JWT_SECRET_KEY)
                .compact();
        this.jwtToken = new JwtToken(tokenValue);
    }


    public String getUsername() {
        Claims claims = JWT_PARSER
                .parseSignedClaims(jwtToken.getValue())
                .getPayload();
        return claims.getSubject();
    }

//    public void setTokenFromRequest(HttpServletRequest request) {
//        String bearerTokenValue = request.getHeader("Authorization");
//        if (bearerTokenValue != null && bearerTokenValue.startsWith("Bearer ")) {
//            jwtToken = new JwtToken(bearerTokenValue.substring(7));
//        }
//    }

    public boolean isValidToken() {
        try{
            JWT_PARSER.parseSignedClaims(jwtToken.getValue());
            return true;
        } catch(Exception e){System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean isValidToken(JwtToken jwtTokenArg) {
        try{
            JWT_PARSER.parseSignedClaims(jwtTokenArg.getValue());
            return true;
        } catch(Exception e){System.out.println(e.getMessage());
            return false;
        }
    }
    public Claims getClaims() {
        return JWT_PARSER
                .parseSignedClaims(jwtToken.getValue())
                .getPayload();
    }
}
