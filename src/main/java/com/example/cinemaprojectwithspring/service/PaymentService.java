package com.example.cinemaprojectwithspring.service;
import com.example.cinemaprojectwithspring.model.response.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO pay(Long ticketId);
}
