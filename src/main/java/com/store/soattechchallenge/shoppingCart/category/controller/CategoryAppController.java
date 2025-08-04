package com.store.soattechchallenge.shoppingCart.category.controller;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.CategoryMapper;
import com.store.soattechchallenge.shoppingCart.category.presenters.CategoryHttpPresenter;
import com.store.soattechchallenge.shoppingCart.category.usecases.*;
import com.store.soattechchallenge.shoppingCart.category.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class CategoryAppController {
    private final CategoryGateway categoryGateway;
    private final CategoryMapper categoryMapper;

    public CategoryAppController(CategoryGateway categoryGateway, CategoryMapper categoryMapper) {
        this.categoryGateway = categoryGateway;
        this.categoryMapper = categoryMapper;
    }

    public CategoryMessagerResponseDTO createCategory(CategoryCommand command) {
        CreateCategoryUseCase createCategoryUseCase = new CreateCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        createCategoryUseCase.saveCategory(command);
        return categoryHttpPresenter.creater();

    }

    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        FindCategoryUseCase findCategoryUseCase = new FindCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        Page<Category> jpaCategories = findCategoryUseCase.getAllCategories(pageable);
        return categoryHttpPresenter.findAllCategories(jpaCategories);
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        FindCategoryUseCase findCategoryUseCase = new FindCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        Optional<Category> jpaCategory = findCategoryUseCase.getCategoryById(id);
        return categoryHttpPresenter.findCategoryById(jpaCategory);
    }

    public CategoryMessagerResponseDTO updateCategory(Long id, CategoryCommand command) {
        UpdateCategoryUseCase updateCategoryUseCase = new UpdateCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        Boolean update =  updateCategoryUseCase.updateCategory(id, command);
        return categoryHttpPresenter.updateCategory(update);
    }

    public CategoryMessagerResponseDTO deleteCategory(Long id) {
        DeleteCategoryUseCase deleteCategoryUseCase = new DeleteCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        Boolean deleted = deleteCategoryUseCase.deleteCategory(id);
        return categoryHttpPresenter.deleteCategory(deleted);
    }

    public Optional<CategoryWithProductsDTO> getProductsByCategoryId(Long id) {
        FindProductsByCategoryUseCase findProductsByCategoryUseCase = new FindProductsByCategoryUseCase(this.categoryGateway);
        CategoryHttpPresenter categoryHttpPresenter = new CategoryHttpPresenter(this.categoryMapper);
        List<CategoryProductProjectionDTO> dtoList = findProductsByCategoryUseCase.getProductsByCategoryId(id);
        return categoryHttpPresenter.findCategoryWithProducts(dtoList);

    }
}
