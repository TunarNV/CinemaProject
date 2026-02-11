package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.response.MovieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
 public interface MovieMapper {

    @Mapping(source = "categoryName",target = "category.name")
    Movie toMovieEntity(MovieRequestDTO movieRequestDTO);

   @Mapping(source = "category.name",target = "categoryName")
   MovieResponseDTO toMovieDTO(Movie movie);
}
