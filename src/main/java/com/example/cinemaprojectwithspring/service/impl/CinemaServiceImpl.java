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
        Cinema cinema = cinemaMapper.toCinemaEntity(dto);
        List<CinemaHall> halls = new ArrayList<>();
        if (dto.getHallNames() != null) {
            for (String hallName : dto.getHallNames()) {
                CinemaHall hall = new CinemaHall();
                hall.setName(hallName);
                hall.setCinema(cinema);
                halls.add(hall);
            }
        }
        cinema.setCinemaHalls(halls);
        Cinema saved = cinemaRepository.save(cinema);
        return cinemaMapper.toCinemaDTO(saved);
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
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id));

        cinema.setName(dto.getName());
        cinema.setLocation(dto.getLocation());
        cinema.setPhoneNumber(dto.getPhoneNumber());
        cinema.setOpenTime(dto.getOpenTime());
        cinema.setCloseTime(dto.getCloseTime());

        if (dto.getHallNames() != null) {
            if (cinema.getCinemaHalls() != null) {
                cinema.getCinemaHalls().clear();
            } else {
                cinema.setCinemaHalls(new ArrayList<>());
            }

            for (String hallName : dto.getHallNames()) {
                CinemaHall hall = new CinemaHall();
                hall.setName(hallName);
                hall.setCinema(cinema);
                cinema.getCinemaHalls().add(hall);
            }
        }

        Cinema updated = cinemaRepository.save(cinema);
        return cinemaMapper.toCinemaDTO(updated);
    }

    @Override
    public void deleteCinema(Long id) {
        if (!cinemaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id);
        }
        cinemaRepository.deleteById(id);
    }
}
