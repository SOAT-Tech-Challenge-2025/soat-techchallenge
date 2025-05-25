package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.model.JpaPayment;

public interface PaymentMapper {
    public Payment toDomain (JpaPayment jpaPayment);
    public PaymentResponseDTO toDTO (Payment payment);
    public JpaPayment toJPA(Payment payment);
}
