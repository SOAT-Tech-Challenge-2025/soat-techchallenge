package com.store.soattechchallenge.payment.infrastructure.api.validator;

import com.store.soattechchallenge.payment.infrastructure.configuration.MercadoPagoIntegrationConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MercadoPagoWebhookValidator {
    private final String API_KEY;

    public MercadoPagoWebhookValidator(MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig) {
        this.API_KEY = mercadoPagoIntegrationConfig.getWebhookToken();
    }

    public void validate(HttpServletRequest request) {
        String apikey = request.getParameter("apikey");
        if (apikey == null || !apikey.equals(this.API_KEY)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }
}
