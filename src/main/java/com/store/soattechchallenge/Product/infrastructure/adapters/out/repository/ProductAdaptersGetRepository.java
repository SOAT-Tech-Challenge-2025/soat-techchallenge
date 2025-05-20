package com.store.soattechchallenge.Product.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.Product.infrastructure.adapters.out.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAdaptersGetRepository extends JpaRepository<ProductEntity,Long> {
}
