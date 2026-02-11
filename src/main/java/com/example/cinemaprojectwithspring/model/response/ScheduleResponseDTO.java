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
public class ScheduleResponseDTO {
    private Long id;
    private String movieName;
    private String cinemaName;
    private String hallName;
    private LocalDateTime startTime;
    private Integer ticketPrice;
}
