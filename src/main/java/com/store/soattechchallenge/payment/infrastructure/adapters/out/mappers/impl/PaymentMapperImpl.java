package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.model.JpaPayment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toDomain(JpaPayment jpaPayment) {
        return new Payment(
                jpaPayment.getId(),
                jpaPayment.getExternalId(),
                jpaPayment.getPaymentStatus(),
                jpaPayment.getTotalOrderValue(),
                jpaPayment.getQrCode(),
                jpaPayment.getExpiration(),
                jpaPayment.getCreatedAt(),
                jpaPayment.getTimestamp()
        );
    }

    @Override
    public PaymentResponseDTO toDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getExternalId(),
                payment.getPaymentStatus(),
                payment.getTotalOrderValue(),
                payment.getQrCode(),
                payment.getExpiration(),
                payment.getCreatedAt(),
                payment.getTimestamp()
        );
    }

    @Override
    public JpaPayment toJPA(Payment payment) {
        return new JpaPayment(payment);
    }
}
