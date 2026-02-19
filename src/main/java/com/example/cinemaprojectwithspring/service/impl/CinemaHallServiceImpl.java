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
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.server.ResponseStatusException;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class CinemaHallServiceImpl implements CinemaHallService {

        private final CinemaHallRepository hallRepository;
        private final CinemaRepository cinemaRepository;
        private final CinemaHallMapper hallMapper;

        @Override
        @Transactional
        public CinemaHallResponseDTO createHall(CinemaHallRequestDTO dto) {
            Cinema cinema = getCinemaById(dto.getCinemaId());

            if (hallRepository.existsByNameAndCinemaId(dto.getName(), dto.getCinemaId()))
                throw new ResponseStatusException
                        (HttpStatus.NOT_FOUND,
                                "Hall exists in this cinema");
            CinemaHall hall = hallMapper.toEntity(dto);
            hall.setCinema(cinema);
            return hallMapper.toDTO(hallRepository.save(hall));
        }

        @Override
        @Transactional(readOnly = true)
        public CinemaHallResponseDTO getHallById(Long id) {
            return hallRepository.findById(id).map(hallMapper::toDTO)
                    .orElseThrow(() -> new ResponseStatusException
                            (HttpStatus.NOT_FOUND,
                                    "Hall not found: " + id));

        }

        @Override
        @Transactional(readOnly = true)
        public List<CinemaHallResponseDTO> getAllHalls() {
            return hallRepository.findAll().stream().map(hallMapper::toDTO).toList();
        }

        @Override
        @Transactional
        public CinemaHallResponseDTO updateHall(Long id, CinemaHallRequestDTO dto) {
            CinemaHall hall = hallRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Hall not found: " + id));
            Cinema cinema = getCinemaById(dto.getCinemaId());

            hall.setName(dto.getName());
            hall.setCinema(cinema);
            return hallMapper.toDTO(hallRepository.save(hall));

        }

        @Override
        @Transactional
        public void deleteHallById(Long id) {
            if (!hallRepository.existsById(id)) throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,
                            "Hall not found: " + id);
            hallRepository.deleteById(id);
        }

        private Cinema getCinemaById(Long id) {
            return cinemaRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Cinema not found: " + id));
        }

    }
