package com.store.soattechchallenge.product.application.service;

import com.store.soattechchallenge.product.application.usecases.ProductUseCases;
import com.store.soattechchallenge.product.domain.model.Product;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.out.repository.impl.ProductRepositoryImpl;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductUseCases {

    public final ProductRepositoryImpl adaptersRepository;

    public ProductServiceImpl(ProductRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public ProductPostUpResponseDTO saveProduct(ProductRequestDTO product) {
        Product ProductRequestModelModel = new Product(product.getProductName(),product.getIdCategory(),product.getUnitPrice(), product.getPreparationTime());
        ProductPostUpResponseDTO responseDTO = new ProductPostUpResponseDTO();
        try {
            adaptersRepository.save(ProductRequestModelModel);
            responseDTO.setMessage("Produto salvo com sucesso");
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao salvar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return responseDTO;
    }

    @Override
    public Page<ProductGetResponseDTO> getAllProducts(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar os produtos",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Optional<ProductGetResponseDTO> getProductById(Long id) {
        try {
            return adaptersRepository.findById(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public ProductPostUpResponseDTO updateProduct(Long id, ProductRequestDTO product) {
        Product Product = new Product(product.getProductName(),product.getIdCategory(),product.getIdCategory(), product.getPreparationTime());
        ProductPostUpResponseDTO ProductPostUpResponseDTO;
        try {
            ProductPostUpResponseDTO = adaptersRepository.update(Product,id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return ProductPostUpResponseDTO;
    }

    @Override
    public ProductPostUpResponseDTO deleteProduct(Long id) {
        try {
           return adaptersRepository.deleteById(id);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao deletar o produto",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }
}