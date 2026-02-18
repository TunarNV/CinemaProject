package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Payment;
import com.example.cinemaprojectwithspring.model.response.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "ticket.id", target = "ticketId")
    @Mapping(source = "ticket.status", target = "ticketStatus")
    PaymentResponseDTO toDTO(Payment payment);
}
