package com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.domain.repository.CategoryRepository;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.CategoryAdaptersRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryAdaptersRepository repository;
    public final CategoryMapper mapper;

    public CategoryRepositoryImpl(CategoryAdaptersRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void save(Category category) {
        repository.save(mapper.categoryToCategoryEntityMap(category));
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void deoleteById(Long id) {

    }
}
