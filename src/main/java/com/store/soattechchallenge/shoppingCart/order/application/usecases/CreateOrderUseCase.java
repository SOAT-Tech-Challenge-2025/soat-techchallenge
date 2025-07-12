package com.store.soattechchallenge.shoppingCart.order.application.usecases;

import com.store.soattechchallenge.shoppingCart.order.application.usecases.command.OrderRequestCommand;
import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.domain.entities.OrderProduct;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.utils.OrderUtils;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.OrderRepositoryGatewaysImpl;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CreateOrderUseCase {
    public final OrderRepositoryGatewaysImpl adaptersRepository;

    public CreateOrderUseCase(OrderRepositoryGatewaysImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public Optional<OrderResponseDTO> saveOrder(OrderRequestCommand command) {
        String orderId = adaptersRepository.orderId();
        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(command.products(), orderId);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(orderId, totalOrder, OrderUtils.somarPreparationTime(command.products()), command.clientId(), orderProducts);
        try {
            adaptersRepository.save(orderRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao gerar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return adaptersRepository.findOrderById(orderId);
    }
}
