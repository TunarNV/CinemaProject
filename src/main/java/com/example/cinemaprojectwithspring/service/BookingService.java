package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;

public interface BookingService {
    TicketResponseDTO reserve(Long sessionId, Long seatId, Long userId);
}
