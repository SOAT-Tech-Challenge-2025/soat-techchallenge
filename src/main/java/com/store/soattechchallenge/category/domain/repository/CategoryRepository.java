package com.store.soattechchallenge.category.domain.repository;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository {

    Category findById(Long id);
    Page<CategoryEntity> findAll(Pageable pageable);
    void save(Category category);
    void update(Category category);
    void deoleteById(Long id);

}
