package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Long> {
}
