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
public class TicketRequestDTO {
    @NotNull
    private Long sessionId;

    @NotNull
    private Long seatId;

    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal price;
}
