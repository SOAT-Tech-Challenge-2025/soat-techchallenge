package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model;

import java.util.List;

public record MPSearchOrderResponse(
        List<MPOrder> elements,
        Integer total
) {

}
