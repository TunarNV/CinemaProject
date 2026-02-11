package com.example.cinemaprojectwithspring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer capacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    private Cinema cinema;
}

