package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.payment.domain.model.PaymentStatus;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentStatusMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusMapperImpl implements PaymentStatusMapper {
    public PaymentStatus toDomain(MPOrderStatus mpStatus) {
        return switch (mpStatus) {
            case opened -> PaymentStatus.OPENED;
            case closed -> PaymentStatus.CLOSED;
            case expired -> PaymentStatus.EXPIRED;
        };
    }
}
