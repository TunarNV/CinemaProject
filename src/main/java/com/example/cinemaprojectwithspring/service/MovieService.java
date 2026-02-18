package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.mapper.MovieMapper;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.response.MovieResponseDTO;
import com.example.cinemaprojectwithspring.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MovieService {

    MovieResponseDTO createMovie(MovieRequestDTO requestDTO);

    List<MovieResponseDTO> getAllMovies();

    MovieResponseDTO getMovieById(Long id);

     void deleteMovieById(Long id);

    MovieResponseDTO update(Long id, MovieRequestDTO movieRequestDTO);

}
