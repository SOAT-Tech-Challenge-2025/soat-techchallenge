package com.store.soattechchallenge.shoppingCart.category.application.usecases;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class FindProductsByCategoryUseCase {
    private final CategoryGatewayGateway adaptersRepository;

    public FindProductsByCategoryUseCase(CategoryGatewayGateway adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Optional<CategoryWithProductsDTO> getProductsByCategoryId(Long id) {
        Optional<CategoryWithProductsDTO> withProductsDTO = adaptersRepository.findProductsByCategoryId(id);
        if (withProductsDTO.isPresent()) {
            return withProductsDTO;
        } else {
            throw new CustomException(
                    "Categoria não encontrada",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

    }
}
