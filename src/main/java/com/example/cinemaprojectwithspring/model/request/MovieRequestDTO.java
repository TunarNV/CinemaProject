package com.example.cinemaprojectwithspring.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class MovieRequestDTO {
    @NotEmpty(message = "Title cannot be empty!")
     String title;
     String description;
     int durationMinutes;
     String genre;
     double rating;
     String categoryName;
     Integer moviePrice;
}
