package com.store.soattechchallenge.product.infrastructure.adapters.in.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductRequestDTO {

    private String productName;
    private Long idCategory;
    private double unitPrice;
    private Long preparationTime;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String productName, Long idCategory, double unitPrice, Long preparationTime) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setuUitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequestDTO that = (ProductRequestDTO) o;
        return Double.compare(that.unitPrice, unitPrice) == 0 && productName.equals(that.productName) && idCategory.equals(that.idCategory) && preparationTime.equals(that.preparationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, idCategory, unitPrice, preparationTime);
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