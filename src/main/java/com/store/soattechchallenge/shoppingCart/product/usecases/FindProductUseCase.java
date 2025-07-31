package com.store.soattechchallenge.shoppingCart.product.usecases;

import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public class FindProductUseCase {

    public final ProductRepositoryGateway adaptersRepository;

    public FindProductUseCase(ProductRepositoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Page<JpaProduct> getAllProducts(Pageable pageable) {
        return adaptersRepository.findAll(pageable);
    }
    public Optional<JpaProduct> getProductById(Long id) {
        return adaptersRepository.findById(id);
    }

}
