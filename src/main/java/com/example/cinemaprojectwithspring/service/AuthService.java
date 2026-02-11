package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.LoginRequestDTO;
import com.example.cinemaprojectwithspring.model.request.UserRequestDTO;
import com.example.cinemaprojectwithspring.model.response.LoginResponseDTO;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(UserRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
