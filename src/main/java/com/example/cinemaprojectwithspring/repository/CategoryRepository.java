package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
    boolean existsByName(String name);
}
