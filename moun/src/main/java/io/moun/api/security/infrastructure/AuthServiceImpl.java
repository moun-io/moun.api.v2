package io.moun.api.security.infrastructure;

import io.moun.api.member.domain.Member;
import io.moun.api.member.service.MemberService;
import io.moun.api.security.controller.dto.CheckRequest;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.repository.AuthRepository;
import io.moun.api.security.domain.repository.RoleRepository;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.exception.UsernameAlreadyExistsException;
import io.moun.api.security.service.AuthService;
import io.moun.api.security.service.IJwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenHelper jwtTokenHelper;


    @Override
    public void save(RegisterRequest registerRequest, Member member) {
        if (authRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }
        Auth auth = new Auth();
        auth.setUsername(registerRequest.getUsername());
        auth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        auth.addRole("ROLE_USER");
        auth.setMember(member);
        authRepository.save(auth);
    }

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwtTokenHelper.generateToken(authentication);
            return jwtTokenHelper.getJwtToken();
        } catch (AuthenticationException e) {
            throw new AuthenticationCredentialsNotFoundException(loginRequest.getUsername());
        }

    }

    @Override
    public void check(CheckRequest checkRequest) {
        boolean isValid = jwtTokenHelper.isValidToken(checkRequest.getJwtToken());
        if (!isValid) {
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }

    }

    @Override
    public Auth findAuthByUsername(String username) {
        Auth auth = authRepository.findByUsername(username).orElse(null);
        if (auth == null) {
            throw new AuthenticationCredentialsNotFoundException(username + "Not Found");
        }
        return auth;
    }
}

