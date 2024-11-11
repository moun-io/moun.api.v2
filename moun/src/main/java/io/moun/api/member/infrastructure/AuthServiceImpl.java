package io.moun.api.member.infrastructure;

import io.moun.api.member.controller.dto.LoginRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Auth;
import io.moun.api.member.domain.Role;
import io.moun.api.member.domain.repository.AuthRepository;
import io.moun.api.member.domain.repository.RoleRepository;
import io.moun.api.member.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public boolean registerAuth(RegisterRequest registerRequest) {
        if(authRepository.existsByUsername(registerRequest.getUsername())) {
            return false;
        }

        Auth auth = new Auth();
        auth.setUsername(registerRequest.getUsername());
        auth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        auth.addRole("ROLE_USER");
        authRepository.save(auth);
        return true;
    }
    @Override
    public boolean loginAuth(LoginRequest loginRequest)  {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }

    }
}
