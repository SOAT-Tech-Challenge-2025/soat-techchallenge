package com.store.soattechchallenge.payment.domain;

import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;

import java.util.List;

public interface PaymentGateway {
    public Payment create(Payment payment, List<Product> products);
}
