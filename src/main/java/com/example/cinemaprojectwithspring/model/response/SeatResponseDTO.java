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
public class SeatResponseDTO {
    private Long id;
    private int row;
    private int number;
    private BigDecimal price;
    private CinemaHallResponseDTO cinemaHall;
}
