package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.mapper.CinemaMapper;
import com.example.cinemaprojectwithspring.model.request.CinemaRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaResponseDTO;
import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.repository.CinemaRepository;
import com.example.cinemaprojectwithspring.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;


    @Override
    @Transactional
    public CinemaResponseDTO createCinema(CinemaRequestDTO dto) {
        log.info("Attempting to create cinema with name: {}", dto.getName());

        if (cinemaRepository.existsByName(dto.getName())) {
            log.warn("Cinema creation failed: cinema with name '{}' already exists", dto.getName());
            throw new RuntimeException("Cinema exists");
        }

        Cinema saved = cinemaRepository.save(cinemaMapper.toCinemaEntity(dto));
        log.info("Cinema '{}' created successfully with id: {}", saved.getName(), saved.getId());
        return cinemaMapper.toCinemaDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CinemaResponseDTO getCinemaById(Long id) {
        log.info("Fetching cinema by id: {}", id);
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cinema not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id);
                });
        log.info("Cinema found: '{}' with id: {}", cinema.getName(), cinema.getId());
        return cinemaMapper.toCinemaDTO(cinema);
    }


    @Override
    @Transactional
    public CinemaResponseDTO updateCinema(Long id, CinemaRequestDTO dto) {
        log.info("Updating cinema with id: {}", id);

        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cinema update failed: not found with id: {}", id);
                    return new RuntimeException("Cinema not found");
                });

        cinema.setName(dto.getName());
        cinema.setAddress(dto.getAddress());
        cinema.setPhoneNumber(dto.getPhoneNumber());
        cinema.setOpenTime(dto.getOpenTime());
        cinema.setCloseTime(dto.getCloseTime());

        Cinema updated = cinemaRepository.save(cinema);
        log.info("Cinema with id: {} updated successfully to name: '{}'", id, updated.getName());
        return cinemaMapper.toCinemaDTO(updated);

    }

    @Override
    @Transactional
    public void deleteCinema(Long id) {
        log.info("Deleting cinema with id: {}", id);

        if (!cinemaRepository.existsById(id)) {
            log.warn("Cinema deletion failed: not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id);
        }

        cinemaRepository.deleteById(id);
        log.info("Cinema with id: {} deleted successfully", id);
    }
}
