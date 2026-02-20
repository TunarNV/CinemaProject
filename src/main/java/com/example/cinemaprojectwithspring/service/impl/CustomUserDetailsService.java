package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.model.enums.UserStatus;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Authenticating user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Authentication failed: user '{}' not found", username);
                    return new UsernameNotFoundException("User not found");
                });

        if (user.getStatus() == UserStatus.BLOCKED) {
            log.warn("Authentication failed: user '{}' is blocked", username);
            throw new RuntimeException("User is blocked");
        }

        log.info("User '{}' authenticated successfully", username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getUserRole().name()))
        );
    }
}
