package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Cinema;
import com.example.cinemaprojectwithspring.entity.CinemaHall;
import com.example.cinemaprojectwithspring.entity.Movie;
import com.example.cinemaprojectwithspring.entity.Schedule;
import com.example.cinemaprojectwithspring.mapper.ScheduleMapper;
import com.example.cinemaprojectwithspring.model.request.ScheduleRequestDTO;
import com.example.cinemaprojectwithspring.model.response.ScheduleResponseDTO;
import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.repository.CinemaRepository;
import com.example.cinemaprojectwithspring.repository.MovieRepository;
import com.example.cinemaprojectwithspring.repository.ScheduleRepository;
import com.example.cinemaprojectwithspring.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final CinemaRepository cinemaRepository; //ToDO Inject CinemaService
    private final CinemaHallRepository cinemaHallRepository; //ToDO Inject CinemaHallService
    private final MovieRepository movieRepository; //ToDO Inject MovieService

    @Transactional
    @Override
    public ScheduleResponseDTO create(ScheduleRequestDTO dto) {
        log.info("Creating schedule: {}", dto);
        Cinema cinema = cinemaRepository.findByName(dto.getCinemaName())
                .orElseThrow(() ->  new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cinema not found: "
                                + dto.getCinemaName()));

        CinemaHall cinemaHall = cinemaHallRepository.findByNameAndCinema(dto.getHallName(), cinema)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cinema hall not found: "
                                + dto.getHallName()));

        Movie movie = movieRepository.findByTitle(dto.getMovieName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + dto.getMovieName()
                ));

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setHall(cinemaHall);
        schedule.setStartTime(dto.getStartTime());
        schedule.setTicketPrice((int)(movie.getMoviePrice() * 0.8));

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleDTO(saved);
    }

    @Override
    public ScheduleResponseDTO getById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Schedule not found: " + id
                ));
        return scheduleMapper.toScheduleDTO(schedule);
    }

    @Override
    public ScheduleResponseDTO update(Long id, ScheduleRequestDTO dto) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Schedule not found: " + id
        ));

        Cinema cinema = cinemaRepository.findByName(dto.getCinemaName())
                .orElseThrow(() ->  new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cinema not found: "
                                + dto.getCinemaName()));

        CinemaHall cinemaHall = cinemaHallRepository.findByNameAndCinema(dto.getHallName(), cinema)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cinema hall not found: "
                                + dto.getHallName()));

        Movie movie = movieRepository.findByTitle(dto.getMovieName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + dto.getMovieName()
                ));
        schedule.setMovie(movie);
        schedule.setHall(cinemaHall);
        schedule.setTicketPrice((int)(movie.getMoviePrice() * 0.8));

        return scheduleMapper.toScheduleDTO(scheduleRepository.save(schedule));
    }

    @Override
    public void deleteById(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found: " + id);
        }
        scheduleRepository.deleteById(id);
    }
}
