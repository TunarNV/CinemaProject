package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.repository.SeatRepository;
import com.example.cinemaprojectwithspring.repository.SessionRepository;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import com.example.cinemaprojectwithspring.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    @Override
    public Ticket reserve(Long sessionId, Long seatId, Long userId) {

        if (ticketRepository.existsBySessionIdAndSeatIdAndStatusIn(
                sessionId, seatId,
                List.of(TicketStatus.RESERVED, TicketStatus.CONFIRMED))) {
            throw new RuntimeException("Seat already booked");
        }

        Ticket ticket = new Ticket();
        ticket.setSession(sessionRepository.findById(sessionId).orElseThrow());
        ticket.setSeat(seatRepository.findById(seatId).orElseThrow());
        ticket.setUser(userRepository.findById(userId).orElseThrow());
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setReservedAt(LocalDateTime.now());
        ticket.setPrice(ticket.getSeat().getPrice());


        return ticketRepository.save(ticket);
    }
}
