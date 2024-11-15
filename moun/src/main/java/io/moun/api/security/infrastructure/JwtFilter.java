package io.moun.api.security.infrastructure;

import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.service.IJwtTokenHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        JwtToken jwtToken = jwtTokenHelper.getJwtToken();
        if(jwtToken !=null && StringUtils.hasText(jwtToken.getValue()) && jwtTokenHelper.isValidToken()) {
            try{
                String username = jwtTokenHelper.getUsername();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (UsernameNotFoundException e) {
                handleException(response,e,500,"Username Not Found Check the Database");
            } catch (Exception e){
                handleException(response,e,500,"An error occurred while logging in");
            }

        }

    }
    private void handleException(HttpServletResponse response, Exception e, int statusCode, String msg) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write("Filter Error: " + msg + "\n" + e +  e.getMessage());
    }
}
