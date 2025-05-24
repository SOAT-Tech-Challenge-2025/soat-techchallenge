package com.store.soattechchallenge.order.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.order.application.service.OrderServiceImpl;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;


    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderPostResponseDTO> createProduct(@RequestBody OrderRequestDTO Product) {
        return ResponseEntity.status(201).body(orderService.saveOrder(Product));
    }

    @GetMapping
    public ResponseEntity<Page<OrderGetResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderGetResponseDTO>> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(orderService.getOrdeById(id));
    }

}