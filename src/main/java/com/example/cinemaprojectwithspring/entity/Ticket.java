package com.example.cinemaprojectwithspring.entity;

import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"session_id", "seat_id"})
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime reservedAt;
}
