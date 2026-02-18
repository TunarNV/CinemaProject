package com.example.cinemaprojectwithspring.repository;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository <Ticket, Long>{
    List<Ticket> findByUserId(Long userId);

    List<Ticket> findBySessionId(Long sessionId);

    boolean existsBySessionIdAndSeatIdAndStatusIn(Long sessionId, Long seatId , List<TicketStatus> statuses);


    @Query("""
           select t.seat.id
           from Ticket t
           where t.session.id = :sessionId
           and t.status in :statuses
           """)
    List<Long> findBookedSeatIds(@Param("sessionId") Long sessionId,
                                 @Param("statuses") List<TicketStatus> statuses);
}
