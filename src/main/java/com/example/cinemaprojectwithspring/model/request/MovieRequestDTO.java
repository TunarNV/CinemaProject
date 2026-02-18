package com.example.cinemaprojectwithspring.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class MovieRequestDTO {
 @NotBlank
  String title;

  String description;

  int durationMinutes;

  String genre;

  double rating;

 @NotNull
  Long categoryId;
}
