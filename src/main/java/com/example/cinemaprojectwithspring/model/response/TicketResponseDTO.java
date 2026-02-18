package com.example.cinemaprojectwithspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private SessionResponseDTO session;
    private SeatResponseDTO seat;
    private UserResponseDTO user;
    private BigDecimal price;
}
