package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;

public class DeleteCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public DeleteCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }
    public Boolean deleteCategory(Long id) {
        return adaptersRepository.deleteById(id);
    }
}
