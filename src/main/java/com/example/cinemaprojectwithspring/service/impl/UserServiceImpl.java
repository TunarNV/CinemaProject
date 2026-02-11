package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.mapper.UserMapper;
import com.example.cinemaprojectwithspring.model.enums.UserStatus;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import com.example.cinemaprojectwithspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void blockUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow();
        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);

    }
}
