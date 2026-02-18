package com.example.cinemaprojectwithspring.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequestDTO {
    @NotNull
    private int row;

    @NotNull
    private int number;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long cinemaHallId;
}
