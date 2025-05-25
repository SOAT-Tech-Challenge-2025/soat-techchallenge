package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentProductCreateRequestDTO(
        @NotBlank
        String name,
        @NotNull
        @Positive
        String category,
        @NotNull
        @Positive
        Double unitPrice,
        @NotNull
        @Positive
        Integer quantity
) {
}
