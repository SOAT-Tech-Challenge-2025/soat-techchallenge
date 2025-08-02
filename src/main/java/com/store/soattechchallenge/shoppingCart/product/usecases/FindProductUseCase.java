package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public class FindProductUseCase {

    public final ProductRepositoryGateway producrRepositoryCateway;

    public FindProductUseCase(ProductRepositoryGateway producrRepositoryCateway) {
        this.producrRepositoryCateway = producrRepositoryCateway;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return producrRepositoryCateway.findAll(pageable);
    }
    public Optional<Product> getProductById(Long id) {
        return producrRepositoryCateway.findById(id);
    }

}
