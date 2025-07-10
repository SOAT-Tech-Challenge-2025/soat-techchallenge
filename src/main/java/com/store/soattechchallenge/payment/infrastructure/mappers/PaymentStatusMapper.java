package com.store.soattechchallenge.payment.infrastructure.mappers;

import com.store.soattechchallenge.payment.domain.PaymentStatus;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPOrderStatus;

public class PaymentStatusMapper {
    public PaymentStatus fromMPToDomain(MPOrderStatus mpStatus) {
        return switch (mpStatus) {
            case opened -> PaymentStatus.OPENED;
            case closed -> PaymentStatus.CLOSED;
            case expired -> PaymentStatus.EXPIRED;
        };
    }
}
