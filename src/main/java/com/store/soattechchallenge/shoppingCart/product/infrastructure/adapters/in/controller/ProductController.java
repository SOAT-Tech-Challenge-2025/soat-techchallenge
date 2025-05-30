package com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.shoppingCart.product.application.service.ProductServiceImpl;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl ProductService;


    public ProductController(ProductServiceImpl ProductService) {
        this.ProductService = ProductService;
    }


    @PostMapping
    public ResponseEntity<ProductPostUpResponseDTO> createProduct(@RequestBody ProductRequestDTO Product) {
        return ResponseEntity.status(201).body(ProductService.saveProduct(Product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductGetResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(ProductService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductGetResponseDTO>> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProductService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPostUpResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO Product) {
        return ResponseEntity.status(200).body(ProductService.updateProduct(id,Product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductPostUpResponseDTO> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProductService.deleteProduct(id));
    }
}