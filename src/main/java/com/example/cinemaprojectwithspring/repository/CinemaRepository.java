package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
}
