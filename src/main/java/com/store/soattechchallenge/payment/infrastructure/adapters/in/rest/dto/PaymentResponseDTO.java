package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.payment.domain.model.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentResponseDTO(
        String id,
        String externalId,
        PaymentStatus paymentStatus,
        Double totalOrderValue,
        String qrCode,
        LocalDateTime expiration,
        LocalDateTime createdAt,
        LocalDateTime timestamp
) {

}
