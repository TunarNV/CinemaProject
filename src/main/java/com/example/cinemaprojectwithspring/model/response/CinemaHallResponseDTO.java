package com.example.cinemaprojectwithspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallResponseDTO {
    private Long id;
    private String name;
    private CinemaResponseDTO cinema;
}
