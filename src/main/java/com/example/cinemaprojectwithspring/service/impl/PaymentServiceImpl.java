package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Payment;
import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.mapper.PaymentMapper;
import com.example.cinemaprojectwithspring.model.enums.PaymentStatus;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.model.response.PaymentResponseDTO;
import com.example.cinemaprojectwithspring.repository.PaymentRepository;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDTO pay(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (ticket.getStatus() != TicketStatus.RESERVED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket not reservable");

        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setAmount(ticket.getPrice());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentTime(LocalDateTime.now());

        ticket.setStatus(TicketStatus.CONFIRMED);

        ticketRepository.save(ticket);
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDTO(savedPayment);
    }
}
