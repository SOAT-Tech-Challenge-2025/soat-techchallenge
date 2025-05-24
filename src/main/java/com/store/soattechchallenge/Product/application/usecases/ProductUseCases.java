package com.store.soattechchallenge.Product.application.usecases;

import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCases {

    ProductPostUpResponseDTO saveProduct(ProductRequestDTO Product);
    Page<ProductGetResponseDTO> getAllProducts(Pageable pageable);
    ProductGetResponseDTO getProductById(Long id);
    ProductPostUpResponseDTO updateProduct(Long id, ProductRequestDTO Product);
    ProductPostUpResponseDTO deleteProduct(Long id);
}
