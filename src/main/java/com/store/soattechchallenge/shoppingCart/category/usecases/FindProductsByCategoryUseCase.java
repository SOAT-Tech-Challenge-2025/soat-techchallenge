package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;

import java.util.List;

public class FindProductsByCategoryUseCase {
    private final CategoryGateway adaptersRepository;

    public FindProductsByCategoryUseCase(CategoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public List<CategoryProductProjectionDTO> getProductsByCategoryId(Long id) {
        return adaptersRepository.findProductsByCategoryId(id);


    }
}
