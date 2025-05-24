package com.store.soattechchallenge.Product.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.Product.domain.model.Product;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.entity.ProductEntity;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.mappers.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ProductMapperImpl implements ProductMapper{

    public Page<ProductGetResponseDTO> modelToProductGetResponseDTO(Page<ProductEntity> products) {
        return products.map(product -> {
            ProductGetResponseDTO productGetResponseDTO = new ProductGetResponseDTO();
            productGetResponseDTO.setId(product.getId());
            productGetResponseDTO.setNameProduct(product.getProductName());
            productGetResponseDTO.setIdCategory(product.getIdCategory());
            productGetResponseDTO.setUnitPrice(product.getUnitPrice().doubleValue());
            productGetResponseDTO.setPreparationTime(product.getPreparationTime().longValue());
            return productGetResponseDTO;
        });
    }

    public Optional<ProductGetResponseDTO> modelToProductGetResponseDTO(Optional<ProductEntity> product) {
        return product.map(p -> {
            ProductGetResponseDTO productGetResponseDTO = new ProductGetResponseDTO();
            productGetResponseDTO.setId(p.getId());
            productGetResponseDTO.setNameProduct(p.getProductName());
            productGetResponseDTO.setIdCategory(p.getIdCategory());
            productGetResponseDTO.setUnitPrice(p.getUnitPrice().doubleValue());
            productGetResponseDTO.setPreparationTime(p.getPreparationTime().longValue());
            return productGetResponseDTO;
        });
    }

    public ProductEntity productToProductUpdateMap(Product product, Long id){

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setProductName(product.getProductName());
        productEntity.setIdCategory(product.getIdCategory());
        productEntity.setUnitPrice(BigDecimal.valueOf(product.getUnitValue()));
        productEntity.setPreparationTime(product.getPreparationTime().intValue());
        productEntity.setInclusionDate(product.getDateInclusion());
        productEntity.setTimestamp(product.getTimestamp());
        return productEntity;
    }

    public ProductEntity productToProductEntityMap(Product product){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(product.getProductName());
        productEntity.setIdCategory(product.getIdCategory());
        productEntity.setUnitPrice(BigDecimal.valueOf(product.getUnitValue()));
        productEntity.setPreparationTime(product.getPreparationTime().intValue());
        productEntity.setInclusionDate(product.getDateInclusion());
        productEntity.setTimestamp(product.getTimestamp());
        return productEntity;
    }

}
