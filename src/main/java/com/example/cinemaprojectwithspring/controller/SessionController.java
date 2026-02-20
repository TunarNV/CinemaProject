package com.example.cinemaprojectwithspring.controller;

import com.example.cinemaprojectwithspring.model.request.SessionRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SessionResponseDTO;
import com.example.cinemaprojectwithspring.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions(){
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO>getById(@PathVariable Long id){
        return ResponseEntity.ok(sessionService.getById(id));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<SessionResponseDTO>>getSessionsByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(sessionService.getSessionsByMovie(movieId));
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO>createSession(@RequestBody SessionRequestDTO sessionRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(sessionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteSession(@PathVariable Long id){
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

}
