package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.exceptions.CreatePagamentoError;
import com.store.soattechchallenge.pagamento.domain.model.GatewayPagamentoResponse;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions.MPClientError;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions.MPNotFoundError;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.*;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class MercadoPagoGatewayPagamento implements GatewayPagamento {
    private final String ACCESS_TOKEN;
    private final String USER_ID;
    private final String POS;
    private final MercadoPagoClient mercadoPagoClient = new MercadoPagoClient(this.ACCESS_TOKEN);

    @Override
    public GatewayPagamentoResponse create(Pagamento pagamento, List<Produto> produtos) {
        List<MPItem> MPItems = produtos.stream()
                .map(produto -> new MPItem(
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidade(),
                        "unit",
                        produto.getValorPorUnidade(),
                        produto.getValorTotal()
                ))
                .collect(Collectors.toList());

        ZoneOffset offset = OffsetDateTime.now().getOffset();
        OffsetDateTime offsetDateTime = pagamento.getExpiracao().atOffset(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String description = "PEDIDO #" + pagamento.getId();
        MPCreateOrderRequest MPCreateOrderRequest = new MPCreateOrderRequest(
                pagamento.getId(),
                pagamento.getVlTotalPedido(),
                description,
                description,
                offsetDateTime.format(formatter),
                MPItems
        );

        MPCreateOrderResponse MPCreateOrderResponse = this.mercadoPagoClient.createDynamicQROrder(
                this.USER_ID, this.POS, MPCreateOrderRequest
        );

        MPOrder MPOrder = this.findOrderByExternalIdWithRetry(pagamento);
        return new GatewayPagamentoResponse(Long.toString(MPOrder.id()), MPCreateOrderResponse.qrData());
    }

    private MPOrder findOrderByExternalIdWithRetry(Pagamento pagamento) {
        int max_attempts = 6;
        int retry_delay_ms = 500;
        String errorMessage = "Ocorreu um erro ao gerar o pagamento";
        for (int attempt = 1; attempt <= max_attempts; attempt++) {
            try {
                return this.mercadoPagoClient.findOrderByExternalId(pagamento.getId());
            } catch (MPNotFoundError e) {
                if (attempt == max_attempts) {
                    throw new CreatePagamentoError(errorMessage);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(retry_delay_ms);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new CreatePagamentoError(errorMessage);
                }
            } catch (MPClientError e) {
                throw new CreatePagamentoError(errorMessage);
            }
        }

        throw new CreatePagamentoError(errorMessage);
    }
}
