package com.example.cinemaprojectwithspring.model.response;

import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

}
