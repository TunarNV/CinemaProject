package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Category;
import com.example.cinemaprojectwithspring.mapper.CategoryMapper;
import com.example.cinemaprojectwithspring.model.request.CategoryRequestDTO;
import com.example.cinemaprojectwithspring.model.response.CategoryResponseDTO;
import com.example.cinemaprojectwithspring.repository.CategoryRepository;
import com.example.cinemaprojectwithspring.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {

        log.info("Attempting to create category with name: {}", dto.getName());

        if (categoryRepository.existsByName(dto.getName())){

            log.warn("Category creation failed: category with name '{}' already exists", dto.getName());

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category exists");
        }
        Category saved = categoryRepository.save(categoryMapper.toEntity(dto));

        log.info("Category '{}' created successfully with id: {}", saved.getName(), saved.getId());

        return categoryMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {

        log.info("Fetching all categories");

        List<CategoryResponseDTO> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();

        log.info("Fetched {} categories", categories.size());

        return categories;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id) {

        log.info("Fetching category by id: {}", id);

        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Category not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id);
                });
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {

        log.info("Updating category with id: {}", id);

        Category category = categoryRepository.findById(id).orElseThrow(() -> {

            log.warn("Category update failed: not found with id: {}", id);

            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id);
        });

        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);

        log.info("Category with id: {} updated successfully to name: {}", id, updated.getName());

        return categoryMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {

        log.info("Deleting category with id: {}", id);

        if (!categoryRepository.existsById(id)) {

            log.warn("Category deletion failed: not found with id: {}", id);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id);
        }
        categoryRepository.deleteById(id);

        log.info("Category with id: {} deleted successfully", id);
    }
}
