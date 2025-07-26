package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.usecases.command.ProductRequestCommand;
import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;

public class CreateProductUseCase {

    public final ProductRepositoryGatewayGateways adaptersRepository;

    public CreateProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public void saveProduct(ProductRequestCommand command) {
        Product ProductRequestModelModel = new Product(command.productName().toUpperCase(),command.idCategory(),command.unitPrice(), command.preparationTime());
        adaptersRepository.save(ProductRequestModelModel);
    }
}
