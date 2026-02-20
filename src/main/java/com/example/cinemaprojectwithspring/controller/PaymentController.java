package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.response.PaymentResponseDTO;
import com.example.cinemaprojectwithspring.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{ticketId}")
    public ResponseEntity<PaymentResponseDTO> pay(@PathVariable Long ticketId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.pay(ticketId));
    }
}
