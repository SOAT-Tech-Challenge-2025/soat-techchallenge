package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.shoppingCart.category.domain.model.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryMapper {

    CategoryEntity toCategoryEntityMap(Category category);
    CategoryEntity toCategoryUpdateMap(Category category, Long id);
    Optional<CategoryWithProductsDTO> toProductCategoryEntity(List<CategoryProductProjectionDTO> projectionDTOList);
}
