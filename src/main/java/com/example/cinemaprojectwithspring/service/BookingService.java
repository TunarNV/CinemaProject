package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.entity.Ticket;

public interface BookingService {
    Ticket reserve(Long sessionId, Long seatId, Long userId);
}
