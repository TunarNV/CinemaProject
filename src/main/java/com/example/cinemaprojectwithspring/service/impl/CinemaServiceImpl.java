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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;


    @Override
    public CinemaResponseDTO createCinema(CinemaRequestDTO dto) {
        if (cinemaRepository.existsByName(dto.getName())) throw new RuntimeException("Cinema exists");
        return cinemaMapper.toCinemaDTO(cinemaRepository.save(cinemaMapper.toCinemaEntity(dto)));
    }

    @Override
    public CinemaResponseDTO getCinemaById(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cinema not found: " + id
                ));
        return cinemaMapper.toCinemaDTO(cinema);
    }


    @Override
    public CinemaResponseDTO updateCinema(Long id, CinemaRequestDTO dto) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
        cinema.setName(dto.getName());
        cinema.setAddress(dto.getAddress());
        cinema.setPhoneNumber(dto.getPhoneNumber());
        cinema.setOpenTime(dto.getOpenTime());
        cinema.setCloseTime(dto.getCloseTime());
        return cinemaMapper.toCinemaDTO(cinemaRepository.save(cinema));
    }

    @Override
    public void deleteCinema(Long id) {
        if (!cinemaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id);
        }
        cinemaRepository.deleteById(id);
    }
}
