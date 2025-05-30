package com.store.soattechchallenge.shoppingCart.product.infrastructure.adapters.in.dto;

import java.math.BigDecimal;

public class ProductGetResponseDTO {

    private Long id;
    private String nameProduct;
    private Long idCategory;
    private BigDecimal unitPrice;
    private Long preparationTime;

    public ProductGetResponseDTO() {
    }

    public ProductGetResponseDTO(Long id, String nameProduct, Long idCategory, BigDecimal unitPrice, Long preparationTime) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.idCategory = idCategory;
        this.unitPrice = unitPrice;
        this.preparationTime = preparationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
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
        return "ProductGetResponseDTO{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", idCategory=" + idCategory +
                ", unitPrice=" + unitPrice +
                ", preparationTime=" + preparationTime +
                '}';
    }
}