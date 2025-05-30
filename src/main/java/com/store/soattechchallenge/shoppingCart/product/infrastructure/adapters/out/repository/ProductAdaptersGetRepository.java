package com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.out.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAdaptersGetRepository extends JpaRepository<ProductEntity,Long> {
    ProductEntity findByProductName(String productName);
}
