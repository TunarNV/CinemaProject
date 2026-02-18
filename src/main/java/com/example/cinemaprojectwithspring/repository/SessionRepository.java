package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository <Session, Long> {
    List<Session> findByMovieId(Long movieId);
    List<Session> findByCinemaHallId(Long cinemaHallId);
    boolean existsByMovieIdAndCinemaHallIdAndStartTime(Long movieId, Long cinemaHallId, LocalDateTime startTime);
}
