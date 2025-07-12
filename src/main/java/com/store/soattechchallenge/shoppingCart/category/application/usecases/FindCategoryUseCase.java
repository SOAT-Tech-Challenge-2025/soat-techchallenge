package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class FindCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public FindCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Page<JpaCategory> getAllCategories(Pageable pageable) {
        return adaptersRepository.findAll(pageable);
    }

    public Optional<JpaCategory> getCategoryById(Long id) {
        return adaptersRepository.findById(id);
    }


}
