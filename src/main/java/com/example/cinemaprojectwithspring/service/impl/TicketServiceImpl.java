package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.mapper.TicketMapper;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        return ticketRepository.findById(id).map(ticketMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public List<TicketResponseDTO> getUserTickets(Long userId) {
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Override
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CONFIRMED) {
            throw new RuntimeException("Use refund process for confirmed tickets");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
    }
}
