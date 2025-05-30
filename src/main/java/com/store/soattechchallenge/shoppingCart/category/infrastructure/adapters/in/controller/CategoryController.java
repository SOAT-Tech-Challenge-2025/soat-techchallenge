package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.shoppingCart.category.application.service.CategoryServiceImpl;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model.CategoryEntity;
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

    private final CategoryServiceImpl categoryService;


    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO category) {
        return ResponseEntity.status(201).body(categoryService.saveCategory(category));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryEntity>> getAllCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit, Sort.by(Sort.Direction.ASC, "id"));
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoryEntity>> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO category) {
        return ResponseEntity.status(200).body(categoryService.updateCategory(id,category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Optional<CategoryWithProductsDTO>> getProductByCategoryId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategoryId(id));
    }
}