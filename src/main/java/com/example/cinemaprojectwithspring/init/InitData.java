package com.example.cinemaprojectwithspring.init;

import com.example.cinemaprojectwithspring.repository.CinemaHallRepository;
import com.example.cinemaprojectwithspring.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final CinemaHallRepository cinemaHallRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cinemaHallRepository.count() == 0){
            initCinemaHalls();
        }
    }

    private void initCinemaHalls(){

    }
}
