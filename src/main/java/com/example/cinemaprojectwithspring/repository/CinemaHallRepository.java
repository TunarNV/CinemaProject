package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.entity.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
    List<CinemaHall> findByCinemaId(Long cinemaId);
    boolean existsByNameAndCinemaId(String name, Long cinemaId);
}
