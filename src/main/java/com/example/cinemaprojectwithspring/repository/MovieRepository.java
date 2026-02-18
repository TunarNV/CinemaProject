package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MovieRepository extends JpaRepository <Movie, Long>{
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByCategoryId(Long categoryId);
}
