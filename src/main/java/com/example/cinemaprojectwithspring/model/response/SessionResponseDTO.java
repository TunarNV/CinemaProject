package com.example.cinemaprojectwithspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponseDTO {
    private Long id;
    private MovieResponseDTO movie;
    private CinemaHallResponseDTO cinemaHall;
    private LocalDateTime startTime;
}
