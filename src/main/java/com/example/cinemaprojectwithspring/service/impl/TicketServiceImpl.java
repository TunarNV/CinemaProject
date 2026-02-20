package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.mapper.TicketMapper;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDTO getTicketById(Long id) {
        log.info("Fetching ticket by id: {}", id);

        return ticketRepository.findById(id)
                .map(ticketMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Ticket not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Ticket not found: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getUserTickets(Long userId) {
        log.info("Fetching tickets for user id: {}", userId);

        List<TicketResponseDTO> tickets = ticketRepository.findByUserId(userId)
                .stream()
                .map(ticketMapper::toDTO)
                .toList();

        log.info("Found {} tickets for user id: {}", tickets.size(), userId);

        return tickets;
    }

    @Override
    @Transactional
    public void cancelTicket(Long ticketId) {
        log.info("Cancelling ticket with id: {}", ticketId);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> {
                    log.warn("Cancel failed. Ticket not found with id: {}", ticketId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Ticket not found: " + ticketId);
                });

        if (ticket.getStatus() == TicketStatus.CONFIRMED) {

            log.warn("Cancel failed. Ticket {} is CONFIRMED. Refund process required.", ticketId);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Use refund process for confirmed tickets");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);

        log.info("Ticket {} cancelled successfully", ticketId);
    }
}
