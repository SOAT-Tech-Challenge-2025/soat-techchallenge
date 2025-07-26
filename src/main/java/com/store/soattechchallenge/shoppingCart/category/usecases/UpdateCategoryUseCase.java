package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;


public class UpdateCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public UpdateCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Boolean updateCategory(Long id, CategoryCommand command) {
        Category category = new Category(command.categoryName());
        return adaptersRepository.update(category,id);

    }
}
