package com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper{

    @Override
    public CategoryEntity categoryToCategoryEntityMap(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setDateInclusion(category.getDateInclusion());
        categoryEntity.setTimestamp(category.getTimestamp());
        return categoryEntity;
    }

    @Override
    public CategoryEntity categoryToCategoryUpdateMap(Category category,Long id){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setDateInclusion(category.getDateInclusion());
        categoryEntity.setTimestamp(category.getTimestamp());
        return categoryEntity;
    }


}
