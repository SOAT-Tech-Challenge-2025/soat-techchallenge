package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.application.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;


public class UpdateCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public UpdateCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryCommand command) {
        Category category = new Category(command.categoryName());
        return adaptersRepository.update(category,id);
    }
}
