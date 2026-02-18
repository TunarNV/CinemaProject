package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Session;
import com.example.cinemaprojectwithspring.model.request.SessionRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SessionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    @Mapping(source = "movie", target = "movie")
    @Mapping(source = "cinemaHall", target = "cinemaHall")
    SessionResponseDTO toDTO(Session session);

    @Mapping(source = "movieId", target = "movie.id")
    @Mapping(source = "cinemaHallId", target = "cinemaHall.id")
    Session toEntity(SessionRequestDTO dto);
}
