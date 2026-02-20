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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO requestDTO) {
        log.info("Attempting to create movie with title: {}", requestDTO.getTitle());

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Movie creation failed: category not found with id: {}", requestDTO.getCategoryId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Category not found: " + requestDTO.getCategoryId());
                });

        Movie movie = movieMapper.toMovieEntity(requestDTO);
        movie.setCategory(category);

        Movie saved = movieRepository.save(movie);

        log.info("Movie '{}' created successfully with id: {}", saved.getTitle(), saved.getId());

        return movieMapper.toMovieDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> getAllMovies() {
        log.info("Fetching all movies");
        List<MovieResponseDTO> movies = movieRepository.findAll()
                .stream()
                .map(movieMapper::toMovieDTO)
                .toList();
        log.info("Fetched {} movies", movies.size());
        return movies;
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponseDTO getMovieById(Long id) {
        log.info("Fetching movie by id: {}", id);

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Movie not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Movie not found: " + id);
                });

        return movieMapper.toMovieDTO(movie);
    }

    @Override
    @Transactional
    public MovieResponseDTO update(Long id, MovieRequestDTO movieRequestDTO){
        log.info("Updating movie with id: {}", id);

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Movie update failed: movie not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Movie not found: " + id);
                });

        Category category = categoryRepository.findById(movieRequestDTO.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Movie update failed: category not found with id: {}", movieRequestDTO.getCategoryId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Category not found: " + movieRequestDTO.getCategoryId());
                });

        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setDurationMinutes(movieRequestDTO.getDurationMinutes());
        movie.setGenre(movieRequestDTO.getGenre());
        movie.setRating(movieRequestDTO.getRating());
        movie.setCategory(category);

        Movie updated = movieRepository.save(movie);

        log.info("Movie with id: {} updated successfully", id);

        return movieMapper.toMovieDTO(updated);
    }

    @Override
    @Transactional
    public void deleteMovieById(Long id) {
        log.info("Deleting movie with id: {}", id);

        if (!movieRepository.existsById(id)) {
            log.warn("Movie deletion failed: movie not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Movie not found: " + id);
        }

        movieRepository.deleteById(id);
        log.info("Movie with id: {} deleted successfully", id);
    }

}
