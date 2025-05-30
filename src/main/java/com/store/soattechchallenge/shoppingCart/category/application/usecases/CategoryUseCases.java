package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryUseCases {

    CategoryResponseDTO saveCategory(CategoryRequestDTO category);
    Page<CategoryEntity> getAllCategories(Pageable pageable);
    Optional<CategoryEntity> getCategoryById(Long id);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO category);
    CategoryResponseDTO deleteCategory(Long id);
}
