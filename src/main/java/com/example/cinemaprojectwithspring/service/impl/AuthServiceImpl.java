package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.jwt.service.JwtService;
import com.example.cinemaprojectwithspring.mapper.UserMapper;
import com.example.cinemaprojectwithspring.model.enums.UserRole;
import com.example.cinemaprojectwithspring.model.enums.UserStatus;
import com.example.cinemaprojectwithspring.model.request.LoginRequestDTO;
import com.example.cinemaprojectwithspring.model.request.UserRequestDTO;
import com.example.cinemaprojectwithspring.model.response.LoginResponseDTO;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import com.example.cinemaprojectwithspring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        request.getUsername(),
                        "",
                        List.of()
                );

        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(request.getUsername(), token);
    }
}

