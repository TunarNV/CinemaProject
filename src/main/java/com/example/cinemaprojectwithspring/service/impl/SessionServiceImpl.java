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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final CinemaHallRepository hallRepository;
    private final SessionMapper sessionMapper;


    @Override
    @Transactional
    public SessionResponseDTO createSession(SessionRequestDTO dto) {


        log.info("Creating session. Movie: {}, Hall: {}, StartTime: {}",
                dto.getMovieId(), dto.getCinemaHallId(), dto.getStartTime());

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> {
                    log.warn("Session creation failed: Movie not found with id: {}", dto.getMovieId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Movie not found: " + dto.getMovieId());
                });

        CinemaHall cinemaHall = hallRepository.findById(dto.getCinemaHallId())
                .orElseThrow(() -> {
                    log.warn("Session creation failed: Hall not found with id: {}", dto.getCinemaHallId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Hall not found: " + dto.getCinemaHallId());
                });

        if (sessionRepository.existsByMovieIdAndCinemaHallIdAndStartTime(
                dto.getMovieId(),
                dto.getCinemaHallId(),
                dto.getStartTime())) {

            log.warn("Session already exists. Movie: {}, Hall: {}, StartTime: {}",
                    dto.getMovieId(), dto.getCinemaHallId(), dto.getStartTime());

            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Session already exists");
        }

        Session session = sessionMapper.toEntity(dto);
        session.setMovie(movie);
        session.setCinemaHall(cinemaHall);

        Session saved = sessionRepository.save(session);

        log.info("Session created successfully with id: {}", saved.getId());

        return sessionMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponseDTO> getAllSessions() {
        log.info("Fetching all sessions");
        List<SessionResponseDTO> sessions = sessionRepository.findAll()
                .stream()
                .map(sessionMapper::toDTO)
                .toList();
        log.info("Fetched {} sessions", sessions.size());
        return sessions;
    }

    @Override
    public List<SessionResponseDTO> getSessionsByMovie(Long movieId) {
        log.info("Fetching sessions by movie id: {}", movieId);

        List<SessionResponseDTO> sessions = sessionRepository.findByMovieId(movieId)
                .stream()
                .map(sessionMapper::toDTO)
                .toList();

        log.info("Found {} sessions for movie id: {}", sessions.size(), movieId);

        return sessions;
    }

    @Override
    @Transactional
    public void deleteSession(Long id) {
        log.info("Deleting session with id: {}", id);

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Session not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Session not found: " + id);
                });

        sessionRepository.delete(session);

        log.info("Session with id: {} deleted successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getById(Long id) {
        log.info("Fetching session by id: {}", id);

        return sessionRepository.findById(id)
                .map(sessionMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Session not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Session not found: " + id);
                });
    }
}
