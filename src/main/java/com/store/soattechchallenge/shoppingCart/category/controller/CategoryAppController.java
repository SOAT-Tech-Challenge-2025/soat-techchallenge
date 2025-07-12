package com.store.soattechchallenge.shoppingCart.category.controller;

import com.store.soattechchallenge.shoppingCart.category.application.usecases.*;
import com.store.soattechchallenge.shoppingCart.category.application.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class CategoryAppController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final FindCategoryUseCase findCategoryUseCase;
    private  final UpdateCategoryUseCase updateCategoryUseCase;
    private  final DeleteCategoryUseCase deleteCategoryUseCase;
    private final FindProductsByCategoryUseCase findProductsByCategoryUseCase;

    public CategoryAppController(CreateCategoryUseCase createCategoryUseCase, FindCategoryUseCase findCategoryUseCase, UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, FindProductsByCategoryUseCase findProductsByCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.findCategoryUseCase = findCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.findProductsByCategoryUseCase = findProductsByCategoryUseCase;
    }

    public CategoryResponseDTO createCategory(CategoryCommand command) {
        return createCategoryUseCase.saveCategory(command);
    }

    public Page<JpaCategory> getAllCategories(Pageable pageable) {
        return findCategoryUseCase.getAllCategories(pageable);
    }

    public Optional<JpaCategory> getCategoryById(Long id) {
        return findCategoryUseCase.getCategoryById(id);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryCommand command) {
        return updateCategoryUseCase.updateCategory(id, command);
    }

    public CategoryResponseDTO deleteCategory(Long id) {
        return deleteCategoryUseCase.deleteCategory(id);
    }

    public Optional<CategoryWithProductsDTO> getProductsByCategoryId(Long id) {
        return findProductsByCategoryUseCase.getProductsByCategoryId(id);

    }
}
