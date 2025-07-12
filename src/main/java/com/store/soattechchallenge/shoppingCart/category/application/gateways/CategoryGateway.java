package com.store.soattechchallenge.shoppingCart.category.application.gateways;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryGateway {

    Optional<JpaCategory> findById(Long id);
    Page<JpaCategory> findAll(Pageable pageable);
    void save(Category category);
    CategoryResponseDTO update(Category category, Long id);
    CategoryResponseDTO deleteById(Long id);
    Optional<CategoryWithProductsDTO> findProductsByCategoryId(Long id);

}
