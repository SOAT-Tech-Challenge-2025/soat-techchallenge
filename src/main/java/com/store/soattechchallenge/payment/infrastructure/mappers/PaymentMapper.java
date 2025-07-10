package com.store.soattechchallenge.payment.infrastructure.mappers;

import com.store.soattechchallenge.payment.domain.entities.Payment;
import com.store.soattechchallenge.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.jpa.JpaPayment;

public class PaymentMapper {
    public Payment fromJpaToDomain(JpaPayment jpaPayment) {
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

    public PaymentResponseDTO fromDomainToDTO(Payment payment) {
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

    public JpaPayment fromDomainToJPA(Payment payment) {
        return new JpaPayment(payment);
    }
}
