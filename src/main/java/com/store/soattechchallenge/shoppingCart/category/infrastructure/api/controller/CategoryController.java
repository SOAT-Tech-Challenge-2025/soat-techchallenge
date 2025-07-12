package com.store.soattechchallenge.shoppingCart.category.infrastructure.api.controller;


import com.store.soattechchallenge.shoppingCart.category.application.usecases.commands.CategoryCommand;
import com.store.soattechchallenge.shoppingCart.category.controller.CategoryAppController;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryRequestDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryAppController categoryAppController;

    public CategoryController(CategoryAppController categoryAppController) {
        this.categoryAppController = categoryAppController;
    }


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO category) {
        CategoryCommand command = new CategoryCommand(category.getCategoryName());
        return ResponseEntity.status(201).body(categoryAppController.createCategory(command));
    }

    @GetMapping
    public ResponseEntity<Page<JpaCategory>> getAllCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit, Sort.by(Sort.Direction.ASC, "id"));
        return ResponseEntity.ok(categoryAppController.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<JpaCategory>> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryAppController.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO category) {
        CategoryCommand command = new CategoryCommand(category.getCategoryName());
        return ResponseEntity.status(200).body(categoryAppController.updateCategory(id,command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryAppController.deleteCategory(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Optional<CategoryWithProductsDTO>> getProductByCategoryId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryAppController.getProductsByCategoryId(id));
    }
}