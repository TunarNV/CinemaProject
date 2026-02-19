package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.SessionRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SessionResponseDTO;

import java.util.List;

public interface SessionService {

    SessionResponseDTO createSession(SessionRequestDTO dto);

    List<SessionResponseDTO> getAllSessions();

    List<SessionResponseDTO> getSessionsByMovie(Long movieId);

    void deleteSession(Long id);
    SessionResponseDTO getById(Long id);

}
