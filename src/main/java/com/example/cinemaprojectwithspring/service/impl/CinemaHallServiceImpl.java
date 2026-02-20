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
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.server.ResponseStatusException;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class CinemaHallServiceImpl implements CinemaHallService {

        private final CinemaHallRepository hallRepository;
        private final CinemaRepository cinemaRepository;
        private final CinemaHallMapper hallMapper;

        @Override
        @Transactional
        public CinemaHallResponseDTO createHall(CinemaHallRequestDTO dto) {
            log.info("Attempting to create hall '{}' for cinema id: {}", dto.getName(), dto.getCinemaId());

            Cinema cinema = getCinemaById(dto.getCinemaId());

            if (hallRepository.existsByNameAndCinemaId(dto.getName(), dto.getCinemaId())) {

                log.warn("Hall creation failed: hall '{}' already exists in cinema id: {}", dto.getName(), dto.getCinemaId());

                throw new ResponseStatusException(HttpStatus.CONFLICT, "Hall already exists in this cinema");
            }

            CinemaHall hall = hallMapper.toEntity(dto);
            hall.setCinema(cinema);
            CinemaHall saved = hallRepository.save(hall);

            log.info("Hall '{}' created successfully with id: {} in cinema id: {}", saved.getName(), saved.getId(), cinema.getId());

            return hallMapper.toDTO(saved);
        }

        @Override
        @Transactional(readOnly = true)
        public CinemaHallResponseDTO getHallById(Long id) {

            log.info("Fetching hall by id: {}", id);

            return hallRepository.findById(id).map(hallMapper::toDTO)
                    .orElseThrow(() -> {

                        log.warn("Hall not found with id: {}", id);

                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found: " + id);
                    });
        }

        @Override
        @Transactional(readOnly = true)
        public List<CinemaHallResponseDTO> getAllHalls() {
            log.info("Fetching all halls");
            List<CinemaHallResponseDTO> halls = hallRepository.findAll().stream()
                    .map(hallMapper::toDTO)
                    .toList();
            log.info("Fetched {} halls", halls.size());
            return halls;
        }

        @Override
        @Transactional
        public CinemaHallResponseDTO updateHall(Long id, CinemaHallRequestDTO dto) {
            log.info("Updating hall with id: {}", id);

            CinemaHall hall = hallRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Hall update failed: not found with id: {}", id);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found: " + id);
                    });

            Cinema cinema = getCinemaById(dto.getCinemaId());

            hall.setName(dto.getName());
            hall.setCinema(cinema);
            CinemaHall updated = hallRepository.save(hall);

            log.info("Hall with id: {} updated successfully to name '{}' in cinema id: {}", id, updated.getName(), cinema.getId());
            return hallMapper.toDTO(updated);

        }

        @Override
        @Transactional
        public void deleteHallById(Long id) {
            log.info("Deleting hall with id: {}", id);
            if (!hallRepository.existsById(id)) {
                log.warn("Hall deletion failed: not found with id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found: " + id);
            }
            hallRepository.deleteById(id);
            log.info("Hall with id: {} deleted successfully", id);
        }

        private Cinema getCinemaById(Long id) {
            log.info("Fetching cinema by id: {}", id);
            return cinemaRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Cinema not found with id: {}", id);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found: " + id);
                    });
        }

    }
