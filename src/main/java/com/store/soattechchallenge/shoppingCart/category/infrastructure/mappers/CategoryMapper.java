package com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryMapper {

    JpaCategory toCategoryEntityMap(Category category);
    JpaCategory toCategoryUpdateMap(Category category, Long id);
    Page<CategoryDTO> toCategoryDTO(Page<Category> categories);
    CategoryDTO toCategoryDTO(Category category);
    Optional<CategoryWithProductsDTO> toProductCategoryEntity(List<CategoryProductProjectionDTO> projectionDTOList);
    Page<Category> entitiesToCategory(Page<JpaCategory> entityPage);
    Optional<Category> entityToCategory(Optional<JpaCategory> entity);
}
