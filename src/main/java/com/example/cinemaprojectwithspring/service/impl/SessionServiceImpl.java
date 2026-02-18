package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.entity.Session;
import com.example.cinemaprojectwithspring.mapper.SessionMapper;
import com.example.cinemaprojectwithspring.model.request.SessionRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SessionResponseDTO;
import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.repository.MovieRepository;
import com.example.cinemaprojectwithspring.repository.SessionRepository;
import com.example.cinemaprojectwithspring.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final CinemaHallRepository hallRepository;
    private final SessionMapper sessionMapper;


    @Override
    public SessionResponseDTO createSession(SessionRequestDTO dto) {

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        CinemaHall cinemaHall = hallRepository.findById(dto.getCinemaHallId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (sessionRepository.existsByMovieIdAndCinemaHallIdAndStartTime(
                dto.getMovieId(), dto.getCinemaHallId(), dto.getStartTime())
        ){
            throw new RuntimeException("Session already exists");
        }

        Session session = sessionMapper.toEntity(dto);
        session.setMovie(movie);
        session.setCinemaHall(cinemaHall);


        return sessionMapper.toDTO(sessionRepository.save(session));
    }

    @Override
    public List<SessionResponseDTO> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapper::toDTO)
                .toList();
    }

    @Override
    public List<SessionResponseDTO> getSessionsByMovie(Long movieId) {
        return sessionRepository.findByMovieId(movieId)
                .stream()
                .map(sessionMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteSession(Long id) {
      if (!sessionRepository.existsById(id)){
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Session not found: " + id);
      }
      sessionRepository.deleteById(id);
    }
}
