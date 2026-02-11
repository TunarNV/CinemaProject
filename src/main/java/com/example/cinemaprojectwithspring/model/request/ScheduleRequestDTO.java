package com.example.cinemaprojectwithspring.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {
    private String cinemaName;
    private String hallName;
    private String movieName;
    private LocalDateTime startTime;
}
