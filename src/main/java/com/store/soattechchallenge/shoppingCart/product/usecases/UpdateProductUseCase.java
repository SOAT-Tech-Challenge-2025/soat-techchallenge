package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.usecases.command.ProductRequestCommand;
import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;


public class UpdateProductUseCase {
    public final ProductRepositoryGateway adaptersRepository;

    public UpdateProductUseCase(ProductRepositoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Boolean updateProduct(Long id, ProductRequestCommand command) {
        Product Product = new Product(command.productName(),command.idCategory(),command.unitPrice(), command.preparationTime());
        return  adaptersRepository.update(Product,id);
    }
}
