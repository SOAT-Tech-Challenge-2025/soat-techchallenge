package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;


public class UpdateCategoryUseCase {
    private final CategoryGateway adaptersRepository;

    public UpdateCategoryUseCase(CategoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Boolean updateCategory(Long id, CategoryCommand command) {
        Category category = new Category(command.categoryName());
        return adaptersRepository.update(category,id);

    }
}
