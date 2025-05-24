package com.store.soattechchallenge.pagamento.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mercado-pago")
public class MercadoPagoConfiguration {
    private String accessToken;
    private String userId;
    private String pos;
    private String callbackUrl;
    private String webhookToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getPos() {
        return pos;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getWebhookToken() { return webhookToken; }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setWebhookToken(String webhookToken) {
        this.webhookToken = webhookToken;
    }
}
