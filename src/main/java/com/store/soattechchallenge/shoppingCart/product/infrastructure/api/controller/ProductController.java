package com.store.soattechchallenge.shoppingCart.product.infrastructure.api.controller;

import com.store.soattechchallenge.shoppingCart.product.usecases.command.ProductRequestCommand;
import com.store.soattechchallenge.shoppingCart.product.controller.ProductMainController;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductRequestDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductMainController controller;


    public ProductController(ProductMainController controller1) {
        this.controller = controller1;
    }


    @PostMapping
    public ResponseEntity<ProductPostUpResponseDTO> createProduct(@RequestBody ProductRequestDTO Product) {
        ProductRequestCommand command = new ProductRequestCommand(
                Product.getProductName(),
                Product.getIdCategory(),
                Product.getUnitPrice(),
                Product.getPreparationTime()
        );
        return ResponseEntity.status(201).body(controller.saveProduct(command));
    }

    @GetMapping
    public ResponseEntity<Page<ProductGetResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(controller.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductGetResponseDTO>> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(controller.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPostUpResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO Product) {
        ProductRequestCommand command = new ProductRequestCommand(
                Product.getProductName(),
                Product.getIdCategory(),
                Product.getUnitPrice(),
                Product.getPreparationTime()
        );
        return ResponseEntity.status(200).body(controller.updateProduct(id,command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductPostUpResponseDTO> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(controller.deleteProduct(id));
    }
}