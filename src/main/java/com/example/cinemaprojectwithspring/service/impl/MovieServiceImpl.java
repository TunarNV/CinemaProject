package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.mapper.MovieMapper;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.response.MovieResponseDTO;
import com.example.cinemaprojectwithspring.repository.CategoryRepository;
import com.example.cinemaprojectwithspring.repository.MovieRepository;
import com.example.cinemaprojectwithspring.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public MovieResponseDTO addMovie(MovieRequestDTO requestDTO) {
        Category category = categoryRepository.findByName(requestDTO.getCategoryName());
        Movie movie = movieMapper.toMovieEntity(requestDTO);
        movie.setCategory(category);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toMovieDTO(savedMovie);
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieDTO).toList();
    }

    @Override
    public MovieResponseDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Movie not found: " + id));
        return movieMapper.toMovieDTO(movie);
    }

    @Override
    public MovieResponseDTO update(Long id, MovieRequestDTO movieRequestDTO){
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Movie not found: " + id));
        Category category;
        if (movieRequestDTO.getCategoryName() != null) {
            category = categoryRepository.findByName(movieRequestDTO.getCategoryName());
            movie.setCategory(category);
        }
        else {
            category = movie.getCategory();
        }
        MovieResponseDTO movieResponseDTO = movieMapper.toMovieDTO(movieRepository.save(movie));
        return movieResponseDTO;
    }

    @Override
    public void deleteMovieById(Long id) {
        if (!movieRepository.existsById(id)) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found: " + id);
        }
        movieRepository.deleteById(id);
    }

}
