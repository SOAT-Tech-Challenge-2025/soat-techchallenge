package com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryMapper {

    JpaCategory toCategoryEntityMap(Category category);
    JpaCategory toCategoryUpdateMap(Category category, Long id);
    Optional<CategoryWithProductsDTO> toProductCategoryEntity(List<CategoryProductProjectionDTO> projectionDTOList);
}
