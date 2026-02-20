package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Seat;
import com.example.cinemaprojectwithspring.entity.Session;
import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.mapper.TicketMapper;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import com.example.cinemaprojectwithspring.repository.SeatRepository;
import com.example.cinemaprojectwithspring.repository.SessionRepository;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.repository.UserRepository;
import com.example.cinemaprojectwithspring.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
    todo
        məsləhətdir ki, bir  service daxilində 1 repo çağrılsın. Digərləri service üzərindən çağrılsın
        Məsələn, UserRepository əvəzinə UserService, SeatService və s. separation of concerns
        bu bütün yerlərə aiddir
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;


    @Override
    @Transactional
    public TicketResponseDTO reserve(Long sessionId, Long seatId, Long userId) {

        log.info("Booking request received: sessionId={}, seatId={}, userId={}", sessionId, seatId, userId);

        if (ticketRepository.existsBySessionIdAndSeatIdAndStatusIn(
                sessionId,
                seatId,
                List.of(TicketStatus.RESERVED, TicketStatus.CONFIRMED))) {

            log.warn("Seat already booked: sessionId={}, seatId={}", sessionId, seatId);

            throw new RuntimeException("Seat already booked");
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> {

                    log.error("Session not found: {}", sessionId);

                    return new RuntimeException("Session not found: " + sessionId);
                });
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> {

                    log.error("Seat not found: {}", seatId);

                    return new RuntimeException("Seat not found: " + seatId);
                });
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {

                    log.error("User not found: {}", userId);

                    return new RuntimeException("User not found: " + userId);
                });


        Ticket ticket = new Ticket();
        ticket.setSession(session);
        ticket.setSeat(seat);
        ticket.setUser(user);
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setReservedAt(LocalDateTime.now());
        ticket.setPrice(seat.getPrice());

        Ticket savedTicket = ticketRepository.save(ticket);

        log.info("Ticket reserved successfully: ticketId={}, userId={}", savedTicket.getId(), userId);

        return ticketMapper.toDTO(savedTicket);
    }
}
