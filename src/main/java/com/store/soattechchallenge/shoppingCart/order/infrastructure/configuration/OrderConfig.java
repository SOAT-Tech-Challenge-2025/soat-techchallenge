package com.store.soattechchallenge.shoppingCart.order.infrastructure.configuration;

import com.store.soattechchallenge.shoppingCart.order.application.usecases.CreateOrderUseCase;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.UpdateOrderUseCase;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
    @Bean
    public OrderMainController orderMainController(
            CreateOrderUseCase createOrderUseCase,
            FindOrdersUseCase findOrdersUseCase,
            UpdateOrderUseCase updateOrderUseCase
    ) {
        return new OrderMainController(createOrderUseCase, findOrdersUseCase, updateOrderUseCase);
    }





}
