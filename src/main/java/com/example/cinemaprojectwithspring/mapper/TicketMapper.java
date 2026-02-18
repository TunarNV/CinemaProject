package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.model.request.TicketRequestDTO;
import com.example.cinemaprojectwithspring.model.response.TicketResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(source = "session", target = "session")
    @Mapping(source = "seat", target = "seat")
    @Mapping(source = "user", target = "user")
    TicketResponseDTO toDTO(Ticket ticket);

    @Mapping(source = "sessionId", target = "session.id")
    @Mapping(source = "seatId", target = "seat.id")
    @Mapping(source = "userId", target = "user.id")
    Ticket toEntity(TicketRequestDTO dto);
}
