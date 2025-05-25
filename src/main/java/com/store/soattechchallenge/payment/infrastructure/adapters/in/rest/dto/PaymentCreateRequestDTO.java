package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.payment.domain.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record PaymentCreateRequestDTO(
        @NotBlank
        String id,
        @NotNull
        @Positive
        Double totalOrderValue,
        @NotEmpty
        List<PaymentProductCreateRequestDTO> products
) {
}
