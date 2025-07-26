package com.store.soattechchallenge.shoppingCart.product.gateways;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;

import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryGateway {

    Optional<JpaProduct> findById(Long id);
    Page<JpaProduct> findAll(Pageable pageable);
    void save(Product Product);
    Boolean update(Product Product, Long id);
    Boolean deleteById(Long id);

}
