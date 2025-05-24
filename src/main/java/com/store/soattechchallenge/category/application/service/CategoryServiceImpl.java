package com.store.soattechchallenge.category.application.service;

import com.store.soattechchallenge.category.application.usecases.CategoryUseCases;
import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.repository.impl.CategoryRepositoryImpl;
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
        Category categoryRequestModelModel = new Category(category.getCategoryName());
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        try {
            adaptersRepository.save(categoryRequestModelModel);
            return new CategoryResponseDTO("Categoria criada com sucesso");
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao criar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao listar categorias: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(Long id) {
        try {
            return adaptersRepository.findById(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        try {
            return adaptersRepository.update(category,id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public CategoryResponseDTO deleteCategory(Long id) {
        try {
           return adaptersRepository.deoleteById(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao deletar categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    public Optional<CategoryWithProductsDTO> getProductsByCategoryId(Long id) {
        Optional<CategoryWithProductsDTO> withProductsDTO;
        try {
            withProductsDTO = adaptersRepository.findProductsByCategoryId(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao pesquisar produtos por categoria: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
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