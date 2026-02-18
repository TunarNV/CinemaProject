package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.mapper.CinemaHallMapper;
import com.example.cinemaprojectwithspring.model.request.CinemaHallRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CinemaHallResponseDTO;
import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.repository.CinemaRepository;
import com.example.cinemaprojectwithspring.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {

    private final CinemaHallRepository hallRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaHallMapper hallMapper;

    /*
       todo
           create üçün mapstruct istifadə edilə bilər.
           cinemaRepository.findById() əlavə metoda çıxarıla bilər.
           RuntimeException əvəzinə başqa exception ola bilər
    */
    @Override
    public CinemaHallResponseDTO createHall(CinemaHallRequestDTO dto) {
        Cinema cinema = cinemaRepository.findById(dto.getCinemaId()).orElseThrow(()->
                new ResponseStatusException
                (HttpStatus.NOT_FOUND,
                        "Cinema not found: " + dto.getCinemaId()));;

        if (hallRepository.existsByNameAndCinemaId(dto.getName(), dto.getCinemaId()))
            throw new RuntimeException("Hall exists in this cinema");

        CinemaHall hall = hallMapper.toEntity(dto);
        hall.setCinema(cinema);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    @Override
    public CinemaHallResponseDTO getHallById(Long id) {
        return hallRepository.findById(id).map(hallMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Hall not found"));
    }

    @Override
    public List<CinemaHallResponseDTO> getAllHalls() {
        return hallRepository.findAll().stream().map(hallMapper::toDTO).toList();
    }

    /*
        todo
            update üçün mapstruct istifadə edilə bilər.
     */
    @Override
    public CinemaHallResponseDTO updateHall(Long id, CinemaHallRequestDTO dto) {
        CinemaHall hall = hallRepository.findById(id).orElseThrow(() -> new RuntimeException("Hall not found"));
        Cinema cinema = cinemaRepository.findById(dto.getCinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));
        hall.setName(dto.getName());
        hall.setCinema(cinema);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    // todo RuntimeException əvəzinə başqa exception ola bilər
    @Override
    public void deleteHallById(Long id) {
        if (!hallRepository.existsById(id)) throw new RuntimeException("Hall not found");
        hallRepository.deleteById(id);
    }

}
