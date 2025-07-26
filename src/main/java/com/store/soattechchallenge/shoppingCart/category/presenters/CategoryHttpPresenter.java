package com.store.soattechchallenge.shoppingCart.category.presenters;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.CategoryMapper;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryHttpPresenter {
    private final CategoryMapper categoryMapper;

    public CategoryHttpPresenter(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public CategoryMessagerResponseDTO creater() {
        return new CategoryMessagerResponseDTO("Categoria criada com sucesso");
    }

    public Page<CategoryDTO> findAllCategories(Page<JpaCategory> jpaCategory) {
        if (jpaCategory == null || jpaCategory.isEmpty()) {
            return Page.empty();
        }
        return categoryMapper.toCategoryDTO(jpaCategory);
    }

    public Optional<CategoryDTO> findCategoryById(Optional<JpaCategory> jpaCategory) {
        if (jpaCategory.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(categoryMapper.toCategoryDTO(jpaCategory.get()));
    }

    public CategoryMessagerResponseDTO updateCategory(Boolean updateCategory) {
        if (updateCategory) {
            return new CategoryMessagerResponseDTO("Categoria atualizada com sucesso");
        } else {
            return new CategoryMessagerResponseDTO("Erro ao atualizar a categoria");
        }
    }

    public CategoryMessagerResponseDTO deleteCategory(Boolean updateCategory) {
        if (updateCategory) {
            return new CategoryMessagerResponseDTO("Categoria deletada com sucesso");
        } else {
            return new CategoryMessagerResponseDTO("Categoria não existe");
        }
    }

    public Optional<CategoryWithProductsDTO> findCategoryWithProducts(List<CategoryProductProjectionDTO> dtos) {
        Optional<CategoryWithProductsDTO> withProductsDTO = categoryMapper.toProductCategoryEntity(dtos);
        if (withProductsDTO.isPresent()) {
            return withProductsDTO;
        } else {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

    }
}
