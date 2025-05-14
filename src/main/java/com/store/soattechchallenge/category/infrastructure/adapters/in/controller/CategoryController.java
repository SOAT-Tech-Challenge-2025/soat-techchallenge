package com.store.soattechchallenge.category.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.category.application.service.CategoryServiceImpl;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;


    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequestDTO category) {
        categoryService.saveCategory(category);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO category) {
        categoryService.updateCategory(id,category);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}