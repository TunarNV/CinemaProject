package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    boolean existsByName(String name);
}
