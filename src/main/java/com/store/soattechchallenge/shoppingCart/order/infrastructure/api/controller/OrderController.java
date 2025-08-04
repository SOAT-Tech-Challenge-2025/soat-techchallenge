package com.store.soattechchallenge.shoppingCart.order.infrastructure.api.controller;

import com.store.soattechchallenge.shoppingCart.order.gateways.EventPublisherGateway;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import com.store.soattechchallenge.shoppingCart.order.usecases.command.OrderRequestCommand;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderRequestDTO;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    public final OrderMainController controller;

    public OrderController(OrderRepositoryGateways orderRepositoryGateways, OrderMapper orderMapper, StreamBridge streamBridge) {
        this.controller = new OrderMainController(orderRepositoryGateways, orderMapper, streamBridge);
    }

    @PostMapping
    public ResponseEntity<Optional<OrderResponseDTO>> createProduct(@RequestBody OrderRequestDTO Product) {
        OrderRequestCommand command = new OrderRequestCommand(Product.getClientId(), Product.getProducts());
        return ResponseEntity.status(201).body(controller.createProduct(command));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(controller.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderResponseDTO>> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(controller.getOrdeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<OrderResponseDTO>> updateProduct(@PathVariable("id") String id, @RequestBody OrderRequestDTO Product) {
        return ResponseEntity.ok(controller.updateOrder(id, Product));
    }

}