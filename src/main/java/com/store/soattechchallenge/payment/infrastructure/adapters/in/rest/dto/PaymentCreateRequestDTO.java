package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.payment.domain.model.Product;

import java.util.List;

public record PaymentCreateRequestDTO(
        String id, Double totalOrderValue, List<Product> products
) {
}
