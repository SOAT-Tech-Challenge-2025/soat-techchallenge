package com.store.soattechchallenge.Product.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.Product.application.service.OrderServiceImpl;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.OrderPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl ProductService;


    public OrderController(OrderServiceImpl ProductService) {
        this.ProductService = ProductService;
    }


    @PostMapping
    public ResponseEntity<OrderPostUpResponseDTO> createProduct(@RequestBody ProductRequestDTO Product) {
        return ResponseEntity.status(201).body(ProductService.saveProduct(Product));
    }

    @GetMapping
    public ResponseEntity<Page<OrderGetResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(ProductService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderGetResponseDTO> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProductService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderPostUpResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO Product) {
        return ResponseEntity.status(200).body(ProductService.updateProduct(id,Product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderPostUpResponseDTO> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProductService.deleteProduct(id));
    }
}