package com.store.soattechchallenge.category.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component

public interface CategoryMapper {

    public CategoryEntity categoryToCategoryEntityMap(Category category);
}
