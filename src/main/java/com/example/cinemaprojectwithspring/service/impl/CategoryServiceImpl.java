package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.mapper.CategoryMapper;
import com.example.cinemaprojectwithspring.model.request.CategoryRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CategoryResponseDTO;
import com.example.cinemaprojectwithspring.repository.CategoryRepository;
import com.example.cinemaprojectwithspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*
    todo
        Bütün service qatlarında Transactional annotationu istifadə edilməlidir
        OSİV (open-session in view haqqında məlumat al)
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        // todo məsləhətdir ki, RuntimeException əvəzinə ya spesifik exception atasan yada ki, ResponseStatusException atasan
        if (categoryRepository.existsByName(dto.getName())) throw new RuntimeException("Category exists");
        return categoryMapper.toDTO(categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
       return categoryRepository.findAll().stream().map(categoryMapper::toDTO).toList();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return  categoryRepository.findById(id).map(categoryMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Movie not found: " + id));
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException
                (HttpStatus.NOT_FOUND,
                "Movie not found: " + id));;
        category.setName(dto.getName());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(Long id) {
      if (!categoryRepository.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found: " + id);
      categoryRepository.deleteById(id);
    }
}
