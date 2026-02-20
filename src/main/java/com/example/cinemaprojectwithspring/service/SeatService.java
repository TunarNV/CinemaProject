package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.SeatRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SeatResponseDTO;

import java.util.List;

public interface SeatService {
    SeatResponseDTO createSeat(SeatRequestDTO dto);

    List<SeatResponseDTO> getSeatsByHall(Long hallId);

    SeatResponseDTO updateSeat(Long id, SeatRequestDTO dto);

    void deleteSeat(Long id);

    List<SeatResponseDTO> getAvailableSeats(Long sessionId);

    SeatResponseDTO getById(Long id);
}
