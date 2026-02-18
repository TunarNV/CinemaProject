package com.example.cinemaprojectwithspring.service;

public interface BookingFacadeService {
   void bookAndPay(Long sessionId, Long seatId, Long userId);
}
