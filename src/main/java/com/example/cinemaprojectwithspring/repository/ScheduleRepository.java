package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository <Schedule, Long> {
}
