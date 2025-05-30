package com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.shoppingCart.product.domain.model.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.out.model.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductMapper {

    Page<ProductGetResponseDTO> modelToProductGetResponseDTO(Page<ProductEntity> products);
    Optional<ProductGetResponseDTO> modelToProductGetResponseDTO(Optional<ProductEntity> product);
    ProductEntity productToProductUpdateMap(Product product, Long id);
    ProductEntity productToProductEntityMap(Product product);
}
