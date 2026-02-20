package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.CinemaRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaResponseDTO;
import com.example.cinemaprojectwithspring.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/{id}")
    public ResponseEntity<CinemaResponseDTO> getCinemaById(@PathVariable Long id){
        return ResponseEntity.ok(cinemaService.getCinemaById(id));
    }

    @PostMapping
    public ResponseEntity<CinemaResponseDTO> createCinema(@RequestBody @Valid CinemaRequestDTO cinemaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaService.createCinema(cinemaRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaResponseDTO>updateMovie(@PathVariable Long id, @RequestBody CinemaRequestDTO cinemaRequestDTO){
        return  ResponseEntity.ok(cinemaService.updateCinema(id,cinemaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteMovieById(@PathVariable Long id){

        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }

}
