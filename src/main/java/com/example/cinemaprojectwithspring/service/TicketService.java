package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;

import java.util.List;

public interface TicketService {
    TicketResponseDTO getTicketById(Long id);

    List<TicketResponseDTO> getUserTickets(Long userId);

    void cancelTicket(Long ticketId);


}
