package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.validator.impl;

import com.store.soattechchallenge.pagamento.configuration.MercadoPagoConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MercadoPagoWebhookValidatorImpl implements MercadoPagoWebhookValidator {
    private final String API_KEY;

    public MercadoPagoWebhookValidatorImpl(MercadoPagoConfiguration mercadoPagoConfiguration) {
        this.API_KEY = mercadoPagoConfiguration.getWebhookToken();
    }

    @Override
    public void validate(HttpServletRequest request) {
        String apikey = request.getParameter("apikey");
        if (apikey == null || !apikey.equals(this.API_KEY)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }
}
