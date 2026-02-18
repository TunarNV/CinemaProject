package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.CinemaHallRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaHallResponseDTO;

import java.util.List;

public interface CinemaHallService {
    CinemaHallResponseDTO createHall(CinemaHallRequestDTO dto);

    CinemaHallResponseDTO getHallById(Long id);

    List<CinemaHallResponseDTO> getAllHalls();

    CinemaHallResponseDTO updateHall(Long id, CinemaHallRequestDTO dto);

    void deleteHallById(Long id);



}
