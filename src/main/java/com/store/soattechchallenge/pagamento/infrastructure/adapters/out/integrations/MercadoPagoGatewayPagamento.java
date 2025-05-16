package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.*;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MercadoPagoGatewayPagamento implements GatewayPagamento {
    private final String CALLBACK_URI = "";
    private final String ACCESS_TOKEN = "";
    private final String USER_ID = "";
    private final String POS = "";
    private final MercadoPagoClient mercadoPagoClient = new MercadoPagoClient(this.ACCESS_TOKEN);

    @Override
    public Pagamento create(Pagamento pagamento, List<Produto> produtos) {
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
        MPCreateOrderRequest mpCreateOrderRequest = new MPCreateOrderRequest(
                pagamento.getId(),
                pagamento.getVlTotalPedido(),
                description,
                description,
                offsetDateTime.format(formatter),
                MPItems,
                this.CALLBACK_URI
        );

        MPCreateOrderResponse MPCreateOrderResponse = this.mercadoPagoClient.createDynamicQROrder(
                this.USER_ID, this.POS, mpCreateOrderRequest
        );

        pagamento.setCodigoQr(MPCreateOrderResponse.qrData());
        return pagamento;
    }
}
