package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.entity.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
    Optional<CinemaHall> findByNameAndCinema(String hallName, Cinema cinema);
}
