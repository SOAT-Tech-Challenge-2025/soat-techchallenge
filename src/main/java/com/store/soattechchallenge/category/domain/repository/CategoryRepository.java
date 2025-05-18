package com.store.soattechchallenge.category.domain.repository;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository {

    Optional<CategoryEntity> findById(Long id);
    Page<CategoryEntity> findAll(Pageable pageable);
    void save(Category category);
    CategoryResponseDTO update(Category category, Long id);
    CategoryResponseDTO deoleteById(Long id);

}
