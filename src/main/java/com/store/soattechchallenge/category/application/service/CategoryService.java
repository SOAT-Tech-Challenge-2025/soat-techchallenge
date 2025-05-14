package com.store.soattechchallenge.category.application.service;

import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void saveCategory(CategoryRequestDTO category);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    void updateCategory(Long id, CategoryRequestDTO category);
    void deleteCategory(Long id);
}
