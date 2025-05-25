package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.payment.domain.model.PaymentStatus;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;

public interface PaymentStatusMapper {
    public PaymentStatus toDomain(MPOrderStatus mpStatus);
}
