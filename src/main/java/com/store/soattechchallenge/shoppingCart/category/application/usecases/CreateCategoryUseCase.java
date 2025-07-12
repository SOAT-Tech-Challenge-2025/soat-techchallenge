package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.application.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;


public class CreateCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public CreateCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public CategoryResponseDTO saveCategory(CategoryCommand category) {
        Category categoryRequestModelModel = new Category(category.categoryName().toUpperCase());
        adaptersRepository.save(categoryRequestModelModel);
        return new CategoryResponseDTO("Categoria criada com sucesso");
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryCommand command) {
        Category category = new Category(command.categoryName());
        return adaptersRepository.update(category,id);
    }
}
