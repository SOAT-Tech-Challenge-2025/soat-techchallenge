package com.store.soattechchallenge.Product.application.service;

import com.store.soattechchallenge.Product.application.usecases.ProductUseCases;
import com.store.soattechchallenge.Product.domain.model.Product;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.repository.impl.ProductRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductUseCases {

    public final ProductRepositoryImpl adaptersRepository;

    public ProductServiceImpl(ProductRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public ProductPostUpResponseDTO saveProduct(ProductRequestDTO product) {
        Product ProductRequestModelModel = new Product(product.getProductName(),product.getIdCategory(),product.getIdCategory(), product.getPreparationTime());
        ProductPostUpResponseDTO responseDTO = new ProductPostUpResponseDTO();
        try {
            adaptersRepository.save(ProductRequestModelModel);
            responseDTO.setMessage("Product created successful");
        }catch (Exception e) {
            throw new RuntimeException("Error saving Product: " + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public Page<ProductGetResponseDTO> getAllProducts(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new RuntimeException("Error getting all categories: " + e.getMessage());
        }
    }

    @Override
    public ProductGetResponseDTO getProductById(Long id) {
        Optional<ProductGetResponseDTO> productEntityOptional;
        try {
            productEntityOptional = adaptersRepository.findById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error getting Product: " + e.getMessage());
        }
        return productEntityOptional.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductPostUpResponseDTO updateProduct(Long id, ProductRequestDTO product) {
        Product Product = new Product(product.getProductName(),product.getIdCategory(),product.getIdCategory(), product.getPreparationTime());
        ProductPostUpResponseDTO ProductPostUpResponseDTO;
        try {
            ProductPostUpResponseDTO = adaptersRepository.update(Product,id);
        }catch (Exception e) {
            throw new RuntimeException("Error updating Product: " + e.getMessage());
        }
        return ProductPostUpResponseDTO;
    }

    @Override
    public ProductPostUpResponseDTO deleteProduct(Long id) {
        try {
           return adaptersRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting Product: " + e.getMessage());
        }
    }
}