package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import com.example.cinemaprojectwithspring.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(ticketService.getUserTickets(userId));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void>cancelTicket(@PathVariable Long ticketId){
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}
