package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.entity.Schedule;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.request.ScheduleRequestDTO;
import com.example.cinemaprojectwithspring.model.response.ScheduleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule toScheduleEntity(ScheduleRequestDTO scheduleRequestDTO);

    @Mapping(target = "movieName", source = "movie.title")
    @Mapping(target = "cinemaName", source = "hall.cinema.name")
    @Mapping(target = "hallName", source = "hall.name")
    ScheduleResponseDTO toScheduleDTO(Schedule schedule);
}
