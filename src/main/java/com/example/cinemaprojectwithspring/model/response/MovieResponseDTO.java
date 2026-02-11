package com.example.cinemaprojectwithspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private String title;
    private String description;
    private int durationMinutes;
    private String genre;
    private double rating;
    String categoryName;
    Integer moviePrice;
}
