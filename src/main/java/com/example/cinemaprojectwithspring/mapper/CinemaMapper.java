package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.model.request.CinemaRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinemaEntity(CinemaRequestDTO cinemaRequestDTO);
    CinemaResponseDTO toCinemaDTO(Cinema cinema);
}
