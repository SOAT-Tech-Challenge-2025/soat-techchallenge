package com.store.soattechchallenge.order.application.usecases;

import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderUseCases {

    OrderPostUpResponseDTO saveProduct(ProductRequestDTO Product);
    Page<OrderGetResponseDTO> getAllProducts(Pageable pageable);
    OrderGetResponseDTO getProductById(Long id);
    OrderPostUpResponseDTO updateProduct(Long id, ProductRequestDTO Product);
    OrderPostUpResponseDTO deleteProduct(Long id);
}
