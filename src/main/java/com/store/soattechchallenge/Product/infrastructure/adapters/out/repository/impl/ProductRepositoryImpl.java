package com.store.soattechchallenge.Product.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.Product.domain.model.Product;
import com.store.soattechchallenge.Product.domain.repository.ProductRepository;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.entity.ProductEntity;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.mappers.ProductMapper;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.repository.ProductAdaptersGetRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
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
        return mapper.modelToProductGetResponseDTO(repository.findById(id));
    }

    @Override
    public Page<ProductGetResponseDTO> findAll(Pageable pageable) {
        return mapper.modelToProductGetResponseDTO(repository.findAll(pageable));
    }

    @Override
    public void save(Product product) {
        repository.save(mapper.productToProductEntityMap(product));
    }

    @Override
    public ProductPostUpResponseDTO update(Product product, Long id) {
        ProductEntity productEntityMapper = mapper.productToProductUpdateMap(product,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        ProductPostUpResponseDTO responseDTO = new ProductPostUpResponseDTO();
        repository.findById(id).ifPresentOrElse(
                productEntity -> {
                    repository.save(productEntityMapper);
                },
                () -> {
                    updated.set(false);
                }
        );
        if(updated.get()) {
            responseDTO.setMessage("Product updated");
            return responseDTO;
        }
        responseDTO.setMessage("Product not updated");
        return responseDTO;
    }

    @Override
    public ProductPostUpResponseDTO deleteById(Long id) {
        Optional<ProductEntity> entity = repository.findById(id);
        ProductPostUpResponseDTO responseDTO = new ProductPostUpResponseDTO();
        if (entity.isPresent()) {
            repository.deleteById(id);
            responseDTO.setMessage("Product deleted");
        }else {
            responseDTO.setMessage("Product not exist");
        }
        return responseDTO;
    }
}
