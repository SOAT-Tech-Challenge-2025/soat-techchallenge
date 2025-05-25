package com.store.soattechchallenge.product.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.product.domain.model.Product;
import com.store.soattechchallenge.product.infrastructure.adapters.in.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.product.infrastructure.adapters.out.entity.ProductEntity;
import com.store.soattechchallenge.product.infrastructure.adapters.out.mappers.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ProductMapperImpl implements ProductMapper{

    public Page<ProductGetResponseDTO> modelToProductGetResponseDTO(Page<ProductEntity> products) {
        List<ProductGetResponseDTO> sortedList = products.getContent().stream()
                .map(product -> {
                    ProductGetResponseDTO dto = new ProductGetResponseDTO();
                    dto.setId(product.getId());
                    dto.setNameProduct(product.getProductName());
                    dto.setIdCategory(product.getIdCategory());
                    dto.setUnitPrice(product.getUnitPrice());
                    dto.setPreparationTime(product.getPreparationTime().longValue());
                    return dto;
                })
                .sorted(Comparator.comparing(ProductGetResponseDTO::getId))
                .toList();
        return new PageImpl<>(sortedList, products.getPageable(), products.getTotalElements());
    }

    public Optional<ProductGetResponseDTO> modelToProductGetResponseDTO(Optional<ProductEntity> product) {
        return product.map(p -> {
            ProductGetResponseDTO productGetResponseDTO = new ProductGetResponseDTO();
            productGetResponseDTO.setId(p.getId());
            productGetResponseDTO.setNameProduct(p.getProductName());
            productGetResponseDTO.setIdCategory(p.getIdCategory());
            productGetResponseDTO.setUnitPrice(p.getUnitPrice());
            productGetResponseDTO.setPreparationTime(p.getPreparationTime().longValue());
            return productGetResponseDTO;
        });
    }

    public ProductEntity productToProductUpdateMap(Product product, Long id){

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setProductName(product.getProductName());
        productEntity.setIdCategory(product.getIdCategory());
        productEntity.setUnitPrice(product.getUnitPrice());
        productEntity.setPreparationTime(product.getPreparationTime().intValue());
        productEntity.setInclusionDate(product.getDateInclusion());
        productEntity.setTimestamp(product.getTimestamp());
        return productEntity;
    }

    public ProductEntity productToProductEntityMap(Product product){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(product.getProductName());
        productEntity.setIdCategory(product.getIdCategory());
        productEntity.setUnitPrice(product.getUnitPrice());
        productEntity.setPreparationTime(product.getPreparationTime().intValue());
        productEntity.setInclusionDate(product.getDateInclusion());
        productEntity.setTimestamp(product.getTimestamp());
        return productEntity;
    }

}
