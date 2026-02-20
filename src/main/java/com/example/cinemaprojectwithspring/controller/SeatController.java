package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.SeatRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SeatResponseDTO;
import com.example.cinemaprojectwithspring.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/{hallId}")
    public ResponseEntity<List<SeatResponseDTO>> getSeatsByHall(@PathVariable Long hallId){
        return ResponseEntity.ok(seatService.getSeatsByHall(hallId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(seatService.getById(id));
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<List<SeatResponseDTO>>getAvailableSeats(@PathVariable Long seatId){
        return ResponseEntity.ok(seatService.getAvailableSeats(seatId));
    }

    @PostMapping
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody SeatRequestDTO seatRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.createSeat(seatRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable Long id, @RequestBody SeatRequestDTO seatRequestDTO){
        return ResponseEntity.ok(seatService.updateSeat(id,seatRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id){
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

}
