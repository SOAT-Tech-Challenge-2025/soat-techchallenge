package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.validator.impl;

import jakarta.servlet.http.HttpServletRequest;

public interface MercadoPagoWebhookValidator {
    public void validate(HttpServletRequest request);
}
