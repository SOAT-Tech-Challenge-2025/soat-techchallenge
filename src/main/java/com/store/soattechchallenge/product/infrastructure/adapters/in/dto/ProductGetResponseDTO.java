package com.store.soattechchallenge.product.infrastructure.adapters.in.dto;

import java.util.Objects;

public class ProductGetResponseDTO {

    private Long id;
    private String nameProduct;
    private Long idCategory;
    private double unitPrice;
    private Long preparationTime;

    public ProductGetResponseDTO() {
    }

    public ProductGetResponseDTO(Long id, String nameProduct, Long idCategory, double unitPrice, Long preparationTime) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
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
        ProductGetResponseDTO that = (ProductGetResponseDTO) o;
        return Double.compare(that.unitPrice, unitPrice) == 0 && id.equals(that.id) && nameProduct.equals(that.nameProduct) && idCategory.equals(that.idCategory) && preparationTime.equals(that.preparationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProduct, idCategory, unitPrice, preparationTime);
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