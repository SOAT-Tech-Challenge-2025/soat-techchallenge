package com.store.soattechchallenge.shoppingCart.order.infrastructure.api.utils;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.OrderProduct;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.ProductRequest;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtils {

    public static List<OrderProduct> groupAndSumProducts(List<ProductRequest> products, String orderId) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductRequest::getProductId,
                        Collectors.toList()
                ))
                .entrySet().stream()
                .map(entry -> {
                    Long productId = entry.getKey();
                    List<ProductRequest> prodList = entry.getValue();
                    int quantidade = prodList.size();
                    double somaVlUnitProduct = prodList.stream()
                            .mapToDouble(ProductRequest::getVlUnitProduct)
                            .sum();
                    return new OrderProduct(orderId, productId, quantidade, somaVlUnitProduct);
                })
                .collect(Collectors.toList());
    }

    public static Integer somarPreparationTime(List<ProductRequest> products) {
        return products.stream()
                .map(ProductRequest::getPreparationTime)
                .filter(java.util.Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
