package com.example.cinemaprojectwithspring.service;

import com.example.cinemaprojectwithspring.model.request.CategoryRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);
    void deleteCategoryById(Long id);
}
