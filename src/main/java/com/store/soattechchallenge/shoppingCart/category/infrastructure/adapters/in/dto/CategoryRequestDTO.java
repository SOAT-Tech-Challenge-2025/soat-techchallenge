package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto;

import org.springframework.stereotype.Component;

@Component
public class CategoryRequestDTO {
    private String categoryName;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryRequestDTO{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryRequestDTO that = (CategoryRequestDTO) o;

        return categoryName != null ? categoryName.equals(that.categoryName) : that.categoryName == null;
    }

    @Override
    public int hashCode() {
        return categoryName != null ? categoryName.hashCode() : 0;
    }
}