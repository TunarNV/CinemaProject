package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.CinemaRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaResponseDTO;


public interface CinemaService {

    CinemaResponseDTO createCinema(CinemaRequestDTO dto);

    CinemaResponseDTO getCinemaById(Long id);

    CinemaResponseDTO updateCinema(Long id,
                                   CinemaRequestDTO dto);
    void deleteCinema(Long id);
}
