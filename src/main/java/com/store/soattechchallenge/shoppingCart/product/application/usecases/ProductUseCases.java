package com.store.soattechchallenge.shoppingCart.product.application.usecases;

import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductUseCases {

    ProductPostUpResponseDTO saveProduct(ProductRequestDTO Product);
    Page<ProductGetResponseDTO> getAllProducts(Pageable pageable);
    Optional<ProductGetResponseDTO> getProductById(Long id);
    ProductPostUpResponseDTO updateProduct(Long id, ProductRequestDTO Product);
    ProductPostUpResponseDTO deleteProduct(Long id);
}
