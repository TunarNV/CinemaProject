package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.LoginRequestDTO;
import com.example.cinemaprojectwithspring.model.request.UserRequestDTO;
import com.example.cinemaprojectwithspring.model.response.LoginResponseDTO;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;
import com.example.cinemaprojectwithspring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
