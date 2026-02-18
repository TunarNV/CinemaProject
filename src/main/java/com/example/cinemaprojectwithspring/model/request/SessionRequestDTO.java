package com.example.cinemaprojectwithspring.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDTO {
    @NotNull
    private Long movieId;

    @NotNull
    private Long cinemaHallId;

    @NotNull
    private LocalDateTime startTime;
}

