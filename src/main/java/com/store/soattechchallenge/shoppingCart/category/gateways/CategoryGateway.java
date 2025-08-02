package com.store.soattechchallenge.shoppingCart.category.gateways;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {

    Optional<Category> findById(Long id);
    Page<Category> findAll(Pageable pageable);
    void save(Category category);
    Boolean update(Category category, Long id);
    Boolean deleteById(Long id);
    List<CategoryProductProjectionDTO> findProductsByCategoryId(Long id);

}
