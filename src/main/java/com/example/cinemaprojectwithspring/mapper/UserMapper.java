package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.User;
import com.example.cinemaprojectwithspring.model.request.UserRequestDTO;
import com.example.cinemaprojectwithspring.model.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "status", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);
}
