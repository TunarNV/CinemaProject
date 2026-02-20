package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.response.MovieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
 public interface MovieMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Movie toMovieEntity(MovieRequestDTO dto);

    @Mapping(source = "category", target = "category")
    MovieResponseDTO toMovieDTO(Movie movie);
}
