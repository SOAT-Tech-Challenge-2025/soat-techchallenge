package com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.payment.domain.PaymentGateway;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.domain.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class MockPaymentGateway implements PaymentGateway {
    @Override
    public Payment create(Payment payment, List<Product> products) {
        LocalDateTime now = LocalDateTime.now();
        return new Payment(
                "1111",
                "11111",
                PaymentStatus.OPENED,
                20.00,
                "doipnqwd",
                LocalDateTime.now().plusMinutes(10),
                now,
                now
        );
    }
}
