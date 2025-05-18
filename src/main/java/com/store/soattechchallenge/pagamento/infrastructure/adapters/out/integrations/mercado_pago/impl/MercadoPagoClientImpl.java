package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.store.soattechchallenge.pagamento.configuration.PagamentoConfiguration;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exception.MPClientException;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exception.MPNotFoundException;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderRequest;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderResponse;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPPayment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class MercadoPagoClientImpl implements MercadoPagoClient {
    private final String ACCESS_TOKEN;
    private final Gson gson;
    private final HttpClient client;

    public MercadoPagoClientImpl(PagamentoConfiguration pagamentoConfiguration) {
        this.ACCESS_TOKEN = pagamentoConfiguration.getAccessToken();
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        this.client = HttpClient.newHttpClient();
    }

    public MPCreateOrderResponse createDynamicQROrder (
            String userId,
            String pos,
            MPCreateOrderRequest MPCreateOrderRequest
    ) {
        String url = String.format(
                "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs",
                userId, pos
        );
        String jsonBody = this.gson.toJson(MPCreateOrderRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.checkResponse(response);
            return this.gson.fromJson(response.body(), MPCreateOrderResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new MPClientException(
                    "An error occurred while making the request to generate the dynamic QR code on Mercado Pago"
            );
        }
    }

    public MPOrder findOrderById(String id) {
        String url = String.format("https://api.mercadopago.com/merchant_orders/%s", id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.checkResponse(response);
            return this.gson.fromJson(response.body(), MPOrder.class);
        } catch (IOException | InterruptedException e) {
            throw new MPClientException(
                    "An error occurred while making the request to retrieve the order on Mercado Pago"
            );
        }
    }

    public MPPayment findPaymentById(String id) {
        String url = String.format("https://api.mercadopago.com/v1/payments/%s", id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.checkResponse(response);
            return this.gson.fromJson(response.body(), MPPayment.class);
        } catch (IOException | InterruptedException e) {
            throw new MPClientException(
                    "An error occurred while making the request to retrieve the payment on Mercado Pago"
            );
        }
    }

    private void checkResponse (HttpResponse<String> response) {
        List<Integer> acceptedStatus = Arrays.asList(200, 201);
        if (response.statusCode() == 404) {
            throw new MPNotFoundException("Not found");
        }

        if (!acceptedStatus.contains(response.statusCode())) {
            throw new MPClientException(
                    "An error occurred while retrieving the response from Mercado Pago | [Status= " +
                            response.statusCode() + "] | [Body] " + response.body()
            );
        }
    }
}
