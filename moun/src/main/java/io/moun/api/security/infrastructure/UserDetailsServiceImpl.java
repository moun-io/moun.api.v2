package io.moun.api.security.infrastructure;

import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.Role;
import io.moun.api.security.domain.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        GrantedAuthority authority = new SimpleGrantedAuthority(auth.getRole().getName());
        return new User(auth.getUsername(), auth.getPassword(), Collections.singletonList(authority));

    }
}
