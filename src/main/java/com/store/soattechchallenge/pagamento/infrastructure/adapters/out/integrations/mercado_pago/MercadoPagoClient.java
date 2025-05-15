package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions.MPClientError;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions.MPNotFoundError;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderRequest;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderResponse;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPSearchOrderResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MercadoPagoClient {
    private final String ACCESS_TOKEN;
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    private final HttpClient client = HttpClient.newHttpClient();

    public MercadoPagoClient(String accessToken) {
        this.ACCESS_TOKEN = accessToken;
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
            throw new MPClientError(
                    "Ocorreu um erro na requisição para gerar o código QR dinâmico no Mercado Pago"
            );
        }
    }

    public MPOrder findOrderByExternalId(String externalId) {
        String url = String.format(
                "https://api.mercadopago.com/merchant_orders/search?external_reference=%s", externalId
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.checkResponse(response);
            MPSearchOrderResponse MPSearchOrderResponse = this.gson.fromJson(
                    response.body(), MPSearchOrderResponse.class
            );

            if (MPSearchOrderResponse.total() == 0) {
                throw new MPNotFoundError("Não encontrado");
            } else if (MPSearchOrderResponse.total() > 1) {
                throw new MPClientError(
                        "Foi encontrada uma quantidade inesperada de itens com o id externo informado: "
                                + MPSearchOrderResponse.total()
                );
            }

            return MPSearchOrderResponse.elements().get(0);
        } catch (IOException | InterruptedException e) {
            throw new MPClientError("Ocorreu um erro na requisição para obter o pagamento no Mercado Pago");
        }
    }

    private void checkResponse (HttpResponse<String> response) {
        List<Integer> acceptedStatus = Arrays.asList(200, 201);
        if (response.statusCode() == 404) {
            throw new MPNotFoundError("Não encontrado");
        }

        if (!acceptedStatus.contains(response.statusCode())) {
            throw new MPClientError(
                    "Ocorreu um erro ao obter a resposta do Mercado Pago, status code " + response.statusCode()
            );
        }
    }
}
