package com.store.soattechchallenge.product.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.product.domain.model.Product;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.out.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductMapper {

    Page<ProductGetResponseDTO> modelToProductGetResponseDTO(Page<ProductEntity> products);
    Optional<ProductGetResponseDTO> modelToProductGetResponseDTO(Optional<ProductEntity> product);
    ProductEntity productToProductUpdateMap(Product product, Long id);
    ProductEntity productToProductEntityMap(Product product);
}
