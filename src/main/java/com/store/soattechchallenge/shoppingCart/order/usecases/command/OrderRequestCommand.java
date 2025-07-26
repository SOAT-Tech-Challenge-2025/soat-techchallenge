package com.store.soattechchallenge.shoppingCart.order.usecases.command;

import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.ProductRequest;
import java.util.List;

public record OrderRequestCommand(String clientId, List<ProductRequest> products) {
}
