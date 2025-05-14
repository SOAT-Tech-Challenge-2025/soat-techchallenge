package com.store.soattechchallenge.category.domain.repository;

import com.store.soattechchallenge.category.domain.model.Category;

import java.util.List;

public interface CategoryRepository {

    Category findById(Long id);
    List<Category> findAll();
    Category save(Category category);
    void update(Category category);
    void deoleteById(Long id);

}
