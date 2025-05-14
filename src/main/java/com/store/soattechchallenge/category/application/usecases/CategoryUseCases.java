package com.store.soattechchallenge.category.application.usecases;

import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryUseCases {

    void saveCategory(CategoryRequestDTO category);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    void updateCategory(Long id, CategoryRequestDTO category);
    void deleteCategory(Long id);
}
