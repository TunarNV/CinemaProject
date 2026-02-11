package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository <Movie, Long>{
    Optional<Movie> findByTitle(String title);
}
