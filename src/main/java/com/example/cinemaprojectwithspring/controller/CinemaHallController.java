package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.CinemaHallRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaHallResponseDTO;
import com.example.cinemaprojectwithspring.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemaHalls")
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    @GetMapping("/{id}")
    public ResponseEntity<CinemaHallResponseDTO> getCinemaHallById(@PathVariable Long id){
        return ResponseEntity.ok(cinemaHallService.getHallById(id));
    }

    @GetMapping
    public ResponseEntity<List<CinemaHallResponseDTO>> getAllHalls(){
        return ResponseEntity.ok(cinemaHallService.getAllHalls());
    }

    @PostMapping
    public ResponseEntity<CinemaHallResponseDTO> createCinemaHall(@RequestBody CinemaHallRequestDTO hallRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaHallService.createHall(hallRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaHallResponseDTO> updateCinemaHall(@PathVariable Long id, @RequestBody CinemaHallRequestDTO hallRequestDTO){
        return ResponseEntity.ok(cinemaHallService.updateHall(id,hallRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinemaHallById(@PathVariable Long id){
        cinemaHallService.deleteHallById(id);
        return ResponseEntity.noContent().build();
    }



}
