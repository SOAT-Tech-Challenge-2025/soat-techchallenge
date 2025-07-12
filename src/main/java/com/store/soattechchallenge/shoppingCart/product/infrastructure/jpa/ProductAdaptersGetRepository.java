package com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAdaptersGetRepository extends JpaRepository<JpaProduct,Long> {
    JpaProduct findByProductName(String productName);
}
