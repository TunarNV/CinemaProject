package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.ScheduleRequestDTO;
import com.example.cinemaprojectwithspring.model.response.ScheduleResponseDTO;;

public interface ScheduleService {

    ScheduleResponseDTO create(ScheduleRequestDTO dto);

    ScheduleResponseDTO getById(Long id);

    ScheduleResponseDTO update(Long id, ScheduleRequestDTO dto);

    void deleteById(Long id);
}
