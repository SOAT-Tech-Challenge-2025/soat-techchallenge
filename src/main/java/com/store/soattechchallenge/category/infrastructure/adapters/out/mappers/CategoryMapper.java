package com.store.soattechchallenge.category.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;

public interface CategoryMapper {

    CategoryEntity categoryToCategoryEntityMap(Category category);
    CategoryEntity categoryToCategoryUpdateMap(Category category, Long id);
}
