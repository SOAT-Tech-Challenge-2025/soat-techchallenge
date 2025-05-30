package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.shoppingCart.category.domain.model.Category;
import com.store.soattechchallenge.shoppingCart.category.domain.repository.CategoryRepository;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model.CategoryEntity;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.mappers.CategoryMapper;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.repository.CategoryAdaptersRepository;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        Optional<CategoryEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return entity;
    }

    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        Page<CategoryEntity> entityPage = repository.findAll(pageable);
        if (entityPage.isEmpty()) {
            throw new CustomException(
                    "Nenhuma categoria encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return entityPage;
    }

    @Override
    public void save(Category category) {
        CategoryEntity exists;
        try {
         exists = repository.findByCategoryName(category.getCategoryName());
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar por nome categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        if (exists != null) {
            throw new CustomException(
                    "Categoria já existe",
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        repository.save(mapper.toCategoryEntityMap(category));
    }

    @Override
    public CategoryResponseDTO update(Category category, Long id) {
        Optional<CategoryEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        CategoryEntity categoryEntityMapper = mapper.toCategoryUpdateMap(category,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        try {
        repository.findById(id).ifPresentOrElse(
                categoryEntity -> {
                    repository.save(categoryEntityMapper);
                },
                () -> {
                    updated.set(false);
                }
        );
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        if(updated.get()) {
            return new CategoryResponseDTO("Categoria atualizada");
        }
        return new CategoryResponseDTO("Categoria não atualizada");
    }

    @Override
    public CategoryResponseDTO deoleteById(Long id) {
        Optional<CategoryEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        if (entity.isPresent()) {
            try {
            repository.deleteProductsByCategoryId(id);
            repository.deleteCategoryById(id);
            return new CategoryResponseDTO("Categoria deletada");
            }catch (Exception e) {
                throw new CustomException(
                        "Erro ao atualizar categoria: " + e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );
            }
        }else {
            return new CategoryResponseDTO("Categoria não existe");
        }
    }

    @Override
    public Optional<CategoryWithProductsDTO> findProductsByCategoryId(Long id) {
        List<Object[]> results;
        try {
        results = repository.findCategoryWithProducts(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao pesquisar produtos por categoria",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
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
