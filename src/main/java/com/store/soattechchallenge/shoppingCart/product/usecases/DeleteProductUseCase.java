package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;

public class DeleteProductUseCase {
    public final ProductRepositoryGatewayGateways adaptersRepository;

    public DeleteProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Boolean deleteProduct(Long id) {
        return adaptersRepository.deleteById(id);
    }
}
