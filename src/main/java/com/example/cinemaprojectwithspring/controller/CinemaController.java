package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.CinemaRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaResponseDTO;
import com.example.cinemaprojectwithspring.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/{id}")
    public CinemaResponseDTO getCinemaById(@PathVariable Long id){
        return cinemaService.getCinemaById(id);
    }

    @PostMapping
    public CinemaResponseDTO createCinema(@RequestBody @Valid CinemaRequestDTO cinemaRequestDTO){
        return cinemaService.createCinema(cinemaRequestDTO);
    }

    @PutMapping("/{id}")
    public CinemaResponseDTO updateMovie(@PathVariable Long id, @RequestBody CinemaRequestDTO cinemaRequestDTO){
        return cinemaService.updateCinema(id,cinemaRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable Long id){
        cinemaService.deleteCinema(id);
    }

}
