package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;

public class DeleteCategoryUseCase {
    private final CategoryGateway adaptersRepository;

    public DeleteCategoryUseCase(CategoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }
    public Boolean deleteCategory(Long id) {
        return adaptersRepository.deleteById(id);
    }
}
