package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;

public class DeleteProductUseCase {
    public final ProductRepositoryGateway adaptersRepository;

    public DeleteProductUseCase(ProductRepositoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Boolean deleteProduct(Long id) {
        return adaptersRepository.deleteById(id);
    }
}
