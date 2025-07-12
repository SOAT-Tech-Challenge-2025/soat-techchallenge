package com.store.soattechchallenge.shoppingCart.product.application.gateways;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryGateway {

    Optional<ProductGetResponseDTO> findById(Long id);
    Page<ProductGetResponseDTO> findAll(Pageable pageable);
    void save(Product Product);
    ProductPostUpResponseDTO update(Product Product, Long id);
    ProductPostUpResponseDTO deleteById(Long id);

}
