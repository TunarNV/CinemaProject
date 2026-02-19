package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    void blockUser(Long id);
    UserResponseDTO getById(Long id);
}
