package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.model.request.MovieRequestDTO;
import com.example.cinemaprojectwithspring.model.response.MovieResponseDTO;
import com.example.cinemaprojectwithspring.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieResponseDTO getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PostMapping
    public MovieResponseDTO createMovie(@RequestBody @Valid MovieRequestDTO requestDTO){
        return movieService.addMovie(requestDTO);
    }

    @PutMapping("/{id}")
    public MovieResponseDTO updateMovie(@PathVariable Long id, @RequestBody MovieRequestDTO movieRequestDTO){
        return movieService.update(id,movieRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }



}
