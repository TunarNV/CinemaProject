package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import com.example.cinemaprojectwithspring.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/reserve")
    public ResponseEntity<TicketResponseDTO> reserve(@RequestParam Long sessionId,
                                                     @RequestParam Long seatId,
                                                     @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.reserve(sessionId, seatId, userId));
    }
}
