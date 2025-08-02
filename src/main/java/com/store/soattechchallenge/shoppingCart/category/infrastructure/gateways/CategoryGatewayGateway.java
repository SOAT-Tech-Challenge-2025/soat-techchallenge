package com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.CategoryMapper;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.CategoryAdaptersRepository;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public class CategoryGatewayGateway implements CategoryGateway {

    private final CategoryAdaptersRepository repository;
    public final CategoryMapper mapper;

    public CategoryGatewayGateway(CategoryAdaptersRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<JpaCategory> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return this.mapper.entityToCategory(entity);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<JpaCategory> entityPage = repository.findAll(pageable);
        if (entityPage.isEmpty()) {
            throw new CustomException(
                    "Nenhuma categoria encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return this.mapper.entitiesToCategory(entityPage);
    }

    @Override
    public void save(Category category) {
        JpaCategory exists = repository.findByCategoryName(category.getCategoryName());

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
    public Boolean update(Category category, Long id) {
        Optional<JpaCategory> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        JpaCategory jpaCategoryMapper = mapper.toCategoryUpdateMap(category,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        try {
        repository.findById(id).ifPresentOrElse(
                categoryEntity -> {
                    repository.save(jpaCategoryMapper);
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
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<JpaCategory> entity = repository.findById(id);
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
            return Boolean.TRUE;
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
            return Boolean.FALSE;
        }
    }

    @Override
    public List<CategoryProductProjectionDTO> findProductsByCategoryId(Long id) {
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
        return dtos;
    }
}
