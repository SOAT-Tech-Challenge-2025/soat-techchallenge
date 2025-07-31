package com.store.soattechchallenge.shoppingCart.category.usecases;

import com.store.soattechchallenge.shoppingCart.category.gateways.CategoryGateway;
import com.store.soattechchallenge.shoppingCart.category.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryMessagerResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;


public class CreateCategoryUseCase {
    private final CategoryGateway adaptersRepository;

    public CreateCategoryUseCase(CategoryGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public void saveCategory(CategoryCommand category) {
        Category categoryRequestModelModel = new Category(category.categoryName().toUpperCase());
        try {
        adaptersRepository.save(categoryRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao cadastrar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

}
