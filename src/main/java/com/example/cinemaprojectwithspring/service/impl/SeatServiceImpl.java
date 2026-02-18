package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.entity.Seat;
import com.example.cinemaprojectwithspring.entity.Session;
import com.example.cinemaprojectwithspring.mapper.SeatMapper;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import com.example.cinemaprojectwithspring.model.request.SeatRequestDTO;
import com.example.cinemaprojectwithspring.model.response.SeatResponseDTO;
import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.repository.SeatRepository;
import com.example.cinemaprojectwithspring.repository.SessionRepository;
import com.example.cinemaprojectwithspring.repository.TicketRepository;
import com.example.cinemaprojectwithspring.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final TicketRepository ticketRepository;
    private final SeatMapper seatMapper;
    private final SessionRepository sessionRepository;


    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO dto) {

        CinemaHall cinemaHall = cinemaHallRepository.findById(dto.getCinemaHallId()).
                orElseThrow(() -> new RuntimeException("Hall not found"));

        if (seatRepository.existsByRowAndNumberAndCinemaHallId(
                dto.getRow(),dto.getNumber(),dto.getCinemaHallId())
        ){
                    throw new RuntimeException("Seat already exists in this hall");
        }

        Seat seat = seatMapper.toEntity(dto);
        seat.setCinemaHall(cinemaHall);

        return seatMapper.toDTO(seatRepository.save(seat));
    }

    @Override
    public List<SeatResponseDTO> getSeatsByHall(Long hallId) {
        return seatRepository.findByCinemaHallId(hallId)
                .stream()
                .map(seatMapper::toDTO)
                .toList();
    }

    @Override
    public SeatResponseDTO updateSeat(Long id, SeatRequestDTO dto) {

        Seat seat = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));

        seat.setRow(dto.getRow());
        seat.setNumber(dto.getNumber());
        seat.setPrice(dto.getPrice());

        return seatMapper.toDTO(seatRepository.save(seat));
    }

    @Override
    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Seat not found: " + id);
        }
        seatRepository.deleteById(id);
    }

    @Override
    public List<SeatResponseDTO> getAvailableSeats(Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        List<Long> bookedSeatIds =
                ticketRepository.findBookedSeatIds(
                        sessionId,
                        List.of(TicketStatus.RESERVED, TicketStatus.CONFIRMED)
                );

        return session.getCinemaHall().getSeats().stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .map(seatMapper::toDTO)
                .toList();
    }
}
