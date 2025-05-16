package com.store.soattechchallenge.category.application.usecases;

import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryUseCases {

    void saveCategory(CategoryRequestDTO category);
    Page<CategoryEntity> getAllCategories(Pageable pageable);
    CategoryEntity getCategoryById(Long id);
    void updateCategory(Long id, CategoryRequestDTO category);
    void deleteCategory(Long id);
}
