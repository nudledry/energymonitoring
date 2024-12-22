package com.grouphoki.energymonitoring.security;

import com.grouphoki.energymonitoring.models.Role;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.repository.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    public CustomUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByUsername(username);

        if (user == null) {
            System.out.println("DEBUG: User not found: " + username);
            throw new UsernameNotFoundException("Username not found");
        }

        System.out.println("DEBUG: Found user: " + username);
        System.out.println("DEBUG: Password from DB: " + user.getPassword());
        System.out.println("DEBUG: Roles: " + user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")));

        Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());
        System.out.println("DEBUG: Mapped Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    String authority = "ROLE_" + role.getName();
                    System.out.println("DEBUG: Mapping role: " + role.getName() + " to authority: " + authority);
                    return new SimpleGrantedAuthority(authority);
                })
                .collect(Collectors.toList());
        return authorities;
    }
}