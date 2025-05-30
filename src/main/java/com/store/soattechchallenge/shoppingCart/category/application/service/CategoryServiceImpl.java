package com.store.soattechchallenge.shoppingCart.category.application.service;

import com.store.soattechchallenge.shoppingCart.category.application.usecases.CategoryUseCases;
import com.store.soattechchallenge.shoppingCart.category.domain.model.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model.CategoryEntity;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.repository.impl.CategoryRepositoryImpl;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class CategoryServiceImpl implements CategoryUseCases {

    public final CategoryRepositoryImpl adaptersRepository;

    public CategoryServiceImpl(CategoryRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO category) {
        Category categoryRequestModelModel = new Category(category.getCategoryName().toUpperCase());
        adaptersRepository.save(categoryRequestModelModel);
        return new CategoryResponseDTO("Categoria criada com sucesso");
    }

    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        return adaptersRepository.findAll(pageable);
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(Long id) {
        return adaptersRepository.findById(id);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        return adaptersRepository.update(category,id);
    }

    @Override
    public CategoryResponseDTO deleteCategory(Long id) {
        return adaptersRepository.deoleteById(id);

    }

    public Optional<CategoryWithProductsDTO> getProductsByCategoryId(Long id) {
        Optional<CategoryWithProductsDTO> withProductsDTO = adaptersRepository.findProductsByCategoryId(id);
            if (withProductsDTO.isPresent()) {
                return withProductsDTO;
            } else {
                throw new CustomException(
                        "Categoria não encontrada",
                        HttpStatus.NOT_FOUND,
                        String.valueOf(HttpStatus.NOT_FOUND.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );
            }

    }
}