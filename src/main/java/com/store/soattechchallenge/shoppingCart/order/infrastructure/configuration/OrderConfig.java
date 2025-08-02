package com.store.soattechchallenge.shoppingCart.order.infrastructure.configuration;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.impl.CategoryMapperImpl;
import com.store.soattechchallenge.shoppingCart.order.gateways.EventPublisherGateway;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.OrderRepositoryGatewaysImpl;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.StreamEventPublisherGateway;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.OrderAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.impl.OrderMapperImpl;
import com.store.soattechchallenge.shoppingCart.order.usecases.CreateOrderUseCase;
import com.store.soattechchallenge.shoppingCart.order.usecases.FindOrdersUseCase;
import com.store.soattechchallenge.shoppingCart.order.usecases.UpdateOrderUseCase;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderConfig {

    @Bean
    public OrderRepositoryGatewaysImpl orderRepositoryGatewaysImpl(OrderAdaptersGetRepository orderRepository, OrderMapper orderMapper) {
        return new OrderRepositoryGatewaysImpl(orderRepository, orderMapper);
    }

    @Bean
    public EventPublisherGateway orderEventPublisherGateway(StreamBridge streamBridge) {
        return new StreamEventPublisherGateway(streamBridge);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepositoryGateways orderRepositoryGateways,
            EventPublisherGateway eventPublisherGateway
    ) {
        return new CreateOrderUseCase(orderRepositoryGateways, eventPublisherGateway);
    }

    @Bean
    public FindOrdersUseCase findOrdersUseCase(OrderRepositoryGateways orderRepositoryGateways) {
        return new FindOrdersUseCase(orderRepositoryGateways);
    }

    @Bean
    public UpdateOrderUseCase updateOrderUseCase(OrderRepositoryGateways orderRepositoryGateways) {
        return new UpdateOrderUseCase(orderRepositoryGateways);
    }

    @Bean
    public OrderMapperImpl orderMapper() {
        return new OrderMapperImpl();
    }

    @Bean
    public OrderMainController orderMainController(
            OrderRepositoryGatewaysImpl adaptersRepository, OrderMapper orderMapper, StreamBridge streamBridge
    ) {
        return new OrderMainController(adaptersRepository, orderMapper, streamBridge);
    }
}
