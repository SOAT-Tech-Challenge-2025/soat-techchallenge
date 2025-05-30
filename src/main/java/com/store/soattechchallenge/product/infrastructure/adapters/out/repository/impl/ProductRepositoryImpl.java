package com.store.soattechchallenge.product.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.product.domain.model.Product;
import com.store.soattechchallenge.product.domain.repository.ProductRepository;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.out.entity.ProductEntity;
import com.store.soattechchallenge.product.infrastructure.adapters.out.mappers.ProductMapper;
import com.store.soattechchallenge.product.infrastructure.adapters.out.repository.ProductAdaptersGetRepository;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductAdaptersGetRepository repository;
    public final ProductMapper mapper;

    public ProductRepositoryImpl(ProductAdaptersGetRepository repository,ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ProductGetResponseDTO> findById(Long id) {
        Optional<ProductEntity> productEntity = repository.findById(id);
        if (productEntity.isEmpty()) {
            throw new CustomException(
                    "Produto não encontrado",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return mapper.modelToProductGetResponseDTO(productEntity);
    }

    @Override
    public Page<ProductGetResponseDTO> findAll(Pageable pageable) {
        Page<ProductEntity> productGetResponseDTOPage = repository.findAll(pageable);
        if (productGetResponseDTOPage.isEmpty()) {
            throw new CustomException(
                    "Nenhum produto encontrado",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return mapper.modelToProductGetResponseDTO(productGetResponseDTOPage);
    }

    @Override
    public void save(Product product) {
        repository.findByProductName(product.getProductName());
        if (repository.findByProductName(product.getProductName()) != null) {
            throw new CustomException(
                    "Produto já existe",
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        try {
        repository.save(mapper.productToProductEntityMap(product));
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao salvar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public ProductPostUpResponseDTO update(Product product, Long id) {
        ProductEntity productEntityMapper = mapper.productToProductUpdateMap(product,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        try {
        repository.findById(id).ifPresentOrElse(
                productEntity -> {
                    repository.save(productEntityMapper);
                },
                () -> {
                    updated.set(false);
                }
        );
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        if(updated.get()) {
            return new ProductPostUpResponseDTO("Produto atualizado com sucesso");
        }
        return new ProductPostUpResponseDTO("Product not updated");

    }

    @Override
    public ProductPostUpResponseDTO deleteById(Long id) {
        Optional<ProductEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            try {
            repository.deleteById(id);
            }catch (Exception e) {
                throw new CustomException(
                        "Erro ao deletar o produto",
                        HttpStatus.BAD_REQUEST,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );}
            return new ProductPostUpResponseDTO("Produto deletado com sucesso");
        }else {
            return new ProductPostUpResponseDTO("Produto não existe");
        }
    }
}
