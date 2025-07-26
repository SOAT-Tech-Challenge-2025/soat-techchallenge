package com.store.soattechchallenge.shoppingCart.order.infrastructure.configuration;

import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.OrderRepositoryGatewaysImpl;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
    @Bean
    public OrderMainController orderMainController(
            OrderRepositoryGatewaysImpl adaptersRepository, OrderMapper orderMapper
    ) {
        return new OrderMainController(adaptersRepository, orderMapper);
    }





}
