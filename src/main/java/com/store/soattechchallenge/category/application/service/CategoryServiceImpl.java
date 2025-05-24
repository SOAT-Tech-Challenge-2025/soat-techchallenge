package com.store.soattechchallenge.category.application.service;

import com.store.soattechchallenge.category.application.usecases.CategoryUseCases;
import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl.CategoryRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryUseCases {

    public final CategoryRepositoryImpl adaptersRepository;

    public CategoryServiceImpl(CategoryRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO category) {
        Category categoryRequestModelModel = new Category(category.getCategoryName());
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        try {
            adaptersRepository.save(categoryRequestModelModel);
            responseDTO.setMessage("Category created successful");
        }catch (Exception e) {
            throw new RuntimeException("Error saving category: " + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new RuntimeException("Error getting all categories: " + e.getMessage());
        }
    }

    @Override
    public CategoryEntity getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional;
        try {
            categoryEntityOptional = adaptersRepository.findById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error getting category: " + e.getMessage());
        }
        return categoryEntityOptional.orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        try {
            categoryResponseDTO = adaptersRepository.update(category,id);
        }catch (Exception e) {
            throw new RuntimeException("Error updating category: " + e.getMessage());
        }
        return categoryResponseDTO;
    }

    @Override
    public CategoryResponseDTO deleteCategory(Long id) {
        try {
           return adaptersRepository.deoleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting category: " + e.getMessage());
        }
    }
}