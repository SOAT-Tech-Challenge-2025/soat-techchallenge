package com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductRequestDTO {

    private String productName;
    private Long idCategory;
    private BigDecimal unitPrice;
    private Long preparationTime;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String productName, Long idCategory, BigDecimal unitPrice, Long preparationTime) {
        this.productName = productName;
        this.idCategory = idCategory;
        this.unitPrice = unitPrice;
        this.preparationTime = preparationTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }


    @Override
    public String toString() {
        return "ProductRequestDTO{" +
                "productName='" + productName + '\'' +
                ", idCategory=" + idCategory +
                ", unitPrice=" + unitPrice +
                ", preparationTime=" + preparationTime +
                '}';
    }
}