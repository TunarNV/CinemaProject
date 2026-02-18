package com.example.cinemaprojectwithspring.mapper;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.model.request.CategoryRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CategoryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDTO toDTO(Category category);
    Category toEntity(CategoryRequestDTO dto);
}
