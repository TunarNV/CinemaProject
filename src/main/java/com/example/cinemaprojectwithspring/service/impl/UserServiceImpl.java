package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.mapper.UserMapper;
import com.example.cinemaprojectwithspring.model.enums.UserStatus;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import com.example.cinemaprojectwithspring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");

        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        log.info("Fetched {} users", users.size());

        return users;
    }

    @Override
    @Transactional
    public void blockUser(Long id) {
        log.info("Blocking user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User not found: " + id);
                });

        if (user.getStatus() == UserStatus.BLOCKED) {
            log.warn("User {} is already blocked", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already blocked");
        }

        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);

        log.info("User {} blocked successfully", id);

    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        log.info("Fetching user by id: {}", id);

        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User not found: " + id);
                });
    }
}
