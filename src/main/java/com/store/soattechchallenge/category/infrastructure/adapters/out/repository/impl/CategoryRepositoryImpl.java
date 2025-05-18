package com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.domain.repository.CategoryRepository;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.CategoryAdaptersRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryAdaptersRepository repository;
    public final CategoryMapper mapper;

    public CategoryRepositoryImpl(CategoryAdaptersRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return repository.findById(id);
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
    public CategoryResponseDTO update(Category category, Long id) {
        CategoryEntity categoryEntityMapper = mapper.categoryToCategoryUpdateMap(category,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        repository.findById(id).ifPresentOrElse(
                categoryEntity -> {
                    repository.save(categoryEntityMapper);
                },
                () -> {
                    updated.set(false);
                }
        );
        if(updated.get()) {
            responseDTO.setMessage("Category updated");
            return responseDTO;
        }
        responseDTO.setMessage("Category not updated");
        return responseDTO;
    }

    @Override
    public CategoryResponseDTO deoleteById(Long id) {
        Optional<CategoryEntity> entity = repository.findById(id);
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        if (entity.isPresent()) {
            repository.deleteById(id);
            responseDTO.setMessage("Category deleted");
        }else {
            responseDTO.setMessage("Category not exist");
        }
        return responseDTO;
    }
}
