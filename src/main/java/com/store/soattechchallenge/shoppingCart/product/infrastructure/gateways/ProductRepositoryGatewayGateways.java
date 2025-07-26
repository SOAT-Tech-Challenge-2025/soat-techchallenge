package com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.ProductAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public class ProductRepositoryGatewayGateways implements ProductRepositoryGateway {

    private final ProductAdaptersGetRepository repository;
    public final ProductMapper mapper;

    public ProductRepositoryGatewayGateways(ProductAdaptersGetRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<JpaProduct> findById(Long id) {
        Optional<JpaProduct> productEntity = repository.findById(id);
        if (productEntity.isEmpty()) {
            throw new CustomException(
                    "Produto não encontrado",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return productEntity;
    }

    @Override
    public Page<JpaProduct> findAll(Pageable pageable) {
        Page<JpaProduct> productGetResponseDTOPage = repository.findAll(pageable);
        if (productGetResponseDTOPage.isEmpty()) {
            throw new CustomException(
                    "Nenhum produto encontrado",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return productGetResponseDTOPage;
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
    public Boolean update(Product product, Long id) {
        JpaProduct jpaProductMapper = mapper.productToProductUpdateMap(product,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        try {
        repository.findById(id).ifPresentOrElse(
                productEntity -> {
                    repository.save(jpaProductMapper);
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
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

    @Override
    public Boolean deleteById(Long id) {
        Optional<JpaProduct> entity = repository.findById(id);
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
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }
}
