package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.model.request.CinemaHallRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaHallResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CinemaHallMapper {
    @Mapping(source = "cinema", target = "cinema")
    CinemaHallResponseDTO toDTO(CinemaHall cinemaHall);

    @Mapping(source = "cinemaId", target = "cinema.id")
    CinemaHall toEntity(CinemaHallRequestDTO dto);
}
