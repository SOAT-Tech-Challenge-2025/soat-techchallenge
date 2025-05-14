package com.store.soattechchallenge.category.application.service;

import com.store.soattechchallenge.category.application.usecases.CategoryUseCases;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryUseCases {


    @Override
    public void saveCategory(CategoryRequestDTO category) {

    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
    }

    @Override
    public void updateCategory(Long id, CategoryRequestDTO categoryDto) {
    }

    @Override
    public void deleteCategory(Long id) {
    }
}