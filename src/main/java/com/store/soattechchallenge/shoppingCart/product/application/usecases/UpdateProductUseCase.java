package com.store.soattechchallenge.shoppingCart.product.application.usecases;

import com.store.soattechchallenge.shoppingCart.product.application.usecases.command.ProductRequestCommand;
import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;


public class UpdateProductUseCase {
    public final ProductRepositoryGatewayGateways adaptersRepository;

    public UpdateProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public ProductPostUpResponseDTO updateProduct(Long id, ProductRequestCommand command) {
        Product Product = new Product(command.productName(),command.idCategory(),command.unitPrice(), command.preparationTime());
        return  adaptersRepository.update(Product,id);
    }
}
