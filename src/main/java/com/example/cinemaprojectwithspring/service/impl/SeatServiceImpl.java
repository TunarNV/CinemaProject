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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final TicketRepository ticketRepository;
    private final SeatMapper seatMapper;
    private final SessionRepository sessionRepository;


    @Override
    @Transactional
    public SeatResponseDTO createSeat(SeatRequestDTO dto) {

        log.info("Creating seat row: {}, number: {} in hall: {}",
                dto.getRow(), dto.getNumber(), dto.getCinemaHallId());

        CinemaHall cinemaHall = cinemaHallRepository.findById(dto.getCinemaHallId())
                .orElseThrow(() -> {
                    log.warn("Hall not found with id: {}", dto.getCinemaHallId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found");
                });

        if (seatRepository.existsByRowNumberAndNumberAndCinemaHallId(
                dto.getRow(), dto.getNumber(), dto.getCinemaHallId())) {

            log.warn("Seat already exists. Row: {}, Number: {}, Hall: {}",
                    dto.getRow(), dto.getNumber(), dto.getCinemaHallId());

            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Seat already exists in this hall");
        }

        Seat seat = seatMapper.toEntity(dto);
        seat.setCinemaHall(cinemaHall);

        Seat saved = seatRepository.save(seat);

        log.info("Seat created successfully with id: {}", saved.getId());

        return seatMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponseDTO> getSeatsByHall(Long hallId) {
        log.info("Fetching seats for hall id: {}", hallId);
        return seatRepository.findByCinemaHallId(hallId)
                .stream()
                .map(seatMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public SeatResponseDTO updateSeat(Long id, SeatRequestDTO dto) {

        log.info("Updating seat with id: {}", id);

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Seat not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Seat not found: " + id);
                });

        seat.setRowNumber(dto.getRow());
        seat.setNumber(dto.getNumber());
        seat.setPrice(dto.getPrice());

        Seat updated = seatRepository.save(seat);

        log.info("Seat with id: {} updated successfully", id);

        return seatMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteSeat(Long id) {
        log.info("Deleting seat with id: {}", id);

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Seat not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Seat not found: " + id);
                });

        seatRepository.delete(seat);

        log.info("Seat with id: {} deleted successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponseDTO> getAvailableSeats(Long sessionId) {

        log.info("Fetching available seats for session id: {}", sessionId);

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> {
                    log.warn("Session not found with id: {}", sessionId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Session not found");
                });

        List<Long> bookedSeatIds =
                ticketRepository.findBookedSeatIds(
                        sessionId,
                        List.of(TicketStatus.RESERVED, TicketStatus.CONFIRMED)
                );

        List<SeatResponseDTO> availableSeats = session.getCinemaHall().getSeats()
                .stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .map(seatMapper::toDTO)
                .toList();

        log.info("Found {} available seats for session id: {}",
                availableSeats.size(), sessionId);

        return availableSeats;

    }

    @Override
    @Transactional(readOnly = true)
    public SeatResponseDTO getById(Long id) {

        log.info("Fetching seat by id: {}", id);

        return seatRepository.findById(id)
                .map(seatMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Seat not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Seat not found: " + id);
                });
    }
}
