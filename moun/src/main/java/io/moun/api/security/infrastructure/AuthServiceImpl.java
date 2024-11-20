package io.moun.api.security.infrastructure;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.mapper.MemberMapper;
import io.moun.api.member.domain.Member;
import io.moun.api.member.domain.repository.MemberRepository;
import io.moun.api.member.service.MemberQueryService;
import io.moun.api.security.controller.dto.LoginRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.controller.dto.LoginResponse;
import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.Role;
import io.moun.api.security.domain.repository.AuthRepository;
import io.moun.api.security.domain.vo.JwtToken;
import io.moun.api.security.exception.UsernameAlreadyExistsException;
import io.moun.api.security.service.AuthService;
import io.moun.api.security.service.IJwtTokenHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenHelper jwtTokenHelper;
    private final ModelMapper modelMapper;
    private final MemberQueryService memberQueryService;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;


    @Override
    @Transactional
    public void save(RegisterRequest registerRequest, Member member) {
        if (authRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }
        Auth auth = new Auth();
        auth.setUsername(registerRequest.getUsername());
        auth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        auth.setRole(Role.USER);
        auth.setMember(member);
        authRepository.save(auth);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Auth auth = authRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(loginRequest.getUsername()));
            Member member = auth.getMember();
            jwtTokenHelper.generateToken(authentication, member.getId());
            MemberResponse memberResponse = memberMapper.toMemberResponse(member);
            JwtToken jwtToken = jwtTokenHelper.getJwtToken();
            return new LoginResponse(jwtToken, memberResponse);
        } catch (AuthenticationException e) {
            throw new AuthenticationCredentialsNotFoundException(loginRequest.getUsername());
        }

    }

    @Override
    public void check() {
        boolean isValid = jwtTokenHelper.isValidToken();
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

