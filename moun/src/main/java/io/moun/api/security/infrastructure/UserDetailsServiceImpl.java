package io.moun.api.security.infrastructure;

import io.moun.api.security.domain.Auth;
import io.moun.api.security.domain.Role;
import io.moun.api.security.domain.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Auth auth = authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(auth.getUsername(), auth.getPassword(), mapRolesToAuthorities(auth.getRoles()));

    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleType())).collect(Collectors.toList());
    }
}
