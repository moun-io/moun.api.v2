package io.moun.api.security.infrastructure;

import io.jsonwebtoken.ExpiredJwtException;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.service.IJwtTokenHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final IJwtTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(IJwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtToken jwt = jwtTokenHelper.getJwtToken();
        //anonymous User
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Authorized
        if (jwtTokenHelper.isValidToken()) {
            logger.info("jwt :" + jwt);
            String username = jwtTokenHelper.getUsername();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            jwtTokenHelper.setJwtToken(null);
        }
        filterChain.doFilter(request, response);
    }
}



