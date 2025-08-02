package com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductMapper {

    Page<ProductGetResponseDTO> modelToProductGetResponsePageDTO(Page<Product> products);
    Optional<ProductGetResponseDTO> modelToProductGetResponseOptionalDTO(Optional<Product> product);
    JpaProduct productToProductUpdateMap(Product product, Long id);
    JpaProduct productToProductEntityMap(Product product);
    Page<Product> productEntityToProduct(Page<JpaProduct> jpaProduct);
    Optional<Product> productEntityToProduct(Optional<JpaProduct> jpaProduct);
}
