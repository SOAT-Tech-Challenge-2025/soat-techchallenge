package com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.impl;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductMapperImpl implements ProductMapper {

    public Page<ProductGetResponseDTO> modelToProductGetResponsePageDTO(Page<Product> products) {
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

    public Optional<ProductGetResponseDTO> modelToProductGetResponseOptionalDTO(Optional<Product> product) {
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

    public JpaProduct productToProductUpdateMap(Product product, Long id){

        JpaProduct jpaProduct = new JpaProduct();
        jpaProduct.setId(id);
        jpaProduct.setProductName(product.getProductName());
        jpaProduct.setIdCategory(product.getIdCategory());
        jpaProduct.setUnitPrice(product.getUnitPrice());
        jpaProduct.setPreparationTime(product.getPreparationTime().intValue());
        jpaProduct.setInclusionDate(product.getDateInclusion());
        jpaProduct.setTimestamp(product.getTimestamp());
        return jpaProduct;
    }

    public JpaProduct productToProductEntityMap(Product product){
        JpaProduct jpaProduct = new JpaProduct();
        jpaProduct.setProductName(product.getProductName());
        jpaProduct.setIdCategory(product.getIdCategory());
        jpaProduct.setUnitPrice(product.getUnitPrice());
        jpaProduct.setPreparationTime(product.getPreparationTime().intValue());
        jpaProduct.setInclusionDate(product.getDateInclusion());
        jpaProduct.setTimestamp(product.getTimestamp());
        return jpaProduct;
    }

    @Override
    public Page<Product> productEntityToProduct(Page<JpaProduct> jpaProducts) {
        if (jpaProducts == null) {
            return null;
        }
        return jpaProducts.map(this::productEntityToProduct);
    }

    @Override
    public Optional<Product> productEntityToProduct(Optional<JpaProduct> jpaProduct) {
        return jpaProduct.map(this::productEntityToProduct);
    }

    public Product productEntityToProduct(JpaProduct jpaProduct) {
        if (jpaProduct == null) {
            return null;
        }
        Product product = new Product();
        product.setId(jpaProduct.getId());
        product.setProductName(jpaProduct.getProductName());
        product.setIdCategory(jpaProduct.getIdCategory());
        product.setUnitPrice(jpaProduct.getUnitPrice());
        product.setPreparationTime(jpaProduct.getPreparationTime() != null ? jpaProduct.getPreparationTime().longValue() : null);
        product.setDateInclusion(jpaProduct.getInclusionDate());
        product.setTimestamp(jpaProduct.getTimestamp());
        return product;
    }

}
