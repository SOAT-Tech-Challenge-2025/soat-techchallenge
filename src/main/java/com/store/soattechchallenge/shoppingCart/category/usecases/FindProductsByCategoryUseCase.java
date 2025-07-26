package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;

import java.util.List;

public class FindProductsByCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public FindProductsByCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public List<CategoryProductProjectionDTO> getProductsByCategoryId(Long id) {
        return adaptersRepository.findProductsByCategoryId(id);


    }
}
