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
    public MovieResponseDTO createMovie(MovieRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found: " + requestDTO.getCategoryId()));
        Movie movie = movieMapper.toMovieEntity(requestDTO);
        movie.setCategory(category);
        return movieMapper.toMovieDTO(movieRepository.save(movie));
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
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException
                (HttpStatus.NOT_FOUND,
                "Movie not found: " + id));
        Category category = categoryRepository.findById(movieRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found: " + movieRequestDTO.getCategoryId()));
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setDurationMinutes(movieRequestDTO.getDurationMinutes());
        movie.setGenre(movieRequestDTO.getGenre());
        movie.setRating(movieRequestDTO.getRating());
        movie.setCategory(category);
        return movieMapper.toMovieDTO(movieRepository.save(movie));
    }

    @Override
    public void deleteMovieById(Long id) {
        if (!movieRepository.existsById(id)) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found: " + id);
        }
        movieRepository.deleteById(id);
    }

}
