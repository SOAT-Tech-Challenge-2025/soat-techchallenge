package com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.domain.repository.CategoryRepository;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.CategoryAdaptersRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
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
        repository.save(mapper.toCategoryEntityMap(category));
    }

    @Override
    public CategoryResponseDTO update(Category category, Long id) {
        CategoryEntity categoryEntityMapper = mapper.toCategoryUpdateMap(category,id);
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

    @Override
    public Optional<CategoryWithProductsDTO> findProductsByCategoryId(Long id) {
        List<Object[]> results = repository.findCategoryWithProducts(id);
        List<CategoryProductProjectionDTO> dtos = results.stream()
                .map(obj -> new CategoryProductProjectionDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        obj[2] != null ? ((Number) obj[2]).longValue() : null,
                        (String) obj[3],
                        obj[4] != null ? ((BigDecimal) obj[4]) : null,
                        obj[5] != null ? ((Number) obj[5]).intValue() : null,
                        (Date) obj[6]
                ))
                .toList();
        return mapper.toProductCategoryEntity(dtos);
    }
}
