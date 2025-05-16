package com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;

public class CategoryMapperImpl implements CategoryMapper{

    public CategoryEntity categoryToCategoryEntityMap(Category category) {
        return CategoryEntity.builder()
                .categoryName(category.getCategoryName())
                .dateInclusion(category.getDateInclusion())
                .timestamp(category.getTimestamp())
                .build();
    }


}
