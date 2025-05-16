package com.store.soattechchallenge.category.application.service;

import com.store.soattechchallenge.category.application.usecases.CategoryUseCases;
import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl.CategoryRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryUseCases {

    public final CategoryRepositoryImpl adaptersRepository;

    public CategoryServiceImpl(CategoryRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public void saveCategory(CategoryRequestDTO category) {
        Category categoryRequestModelModel = new Category(category.getCategoryName());
        try {
            adaptersRepository.save(categoryRequestModelModel);
        }catch (Exception e) {
            throw new RuntimeException("Error saving category: " + e.getMessage());
        }
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
    }

    @Override
    public void updateCategory(Long id, CategoryRequestDTO categoryDto) {
    }

    @Override
    public void deleteCategory(Long id) {
    }
}