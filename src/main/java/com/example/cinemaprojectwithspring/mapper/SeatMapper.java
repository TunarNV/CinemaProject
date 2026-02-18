package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Seat;
import com.example.cinemaprojectwithspring.model.request.SeatRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SeatResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(source = "cinemaHall", target = "cinemaHall")
    SeatResponseDTO toDTO(Seat seat);

    @Mapping(source = "cinemaHallId", target = "cinemaHall.id")
    Seat toEntity(SeatRequestDTO dto);
}
