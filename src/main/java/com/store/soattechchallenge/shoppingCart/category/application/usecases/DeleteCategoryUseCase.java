package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;

public class DeleteCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public DeleteCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }
    public CategoryResponseDTO deleteCategory(Long id) {
        return adaptersRepository.deleteById(id);
    }
}
