package com.store.soattechchallenge.category.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.category.application.service.CategoryServiceImpl;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<CategoryEntity>> getCategories(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        int calculatedPage = (offset / limit) + page;
        Pageable pageable = PageRequest.of(calculatedPage, limit);
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategory(@PathVariable("id") Long id) {
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
}