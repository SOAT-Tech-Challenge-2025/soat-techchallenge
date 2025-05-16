package com.store.soattechchallenge.category.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAdaptersRepository extends JpaRepository<CategoryEntity,Long> {
}
