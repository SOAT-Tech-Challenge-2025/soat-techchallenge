package com.store.soattechchallenge.Product.domain.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.Objects;

public class Product {

    private String productName;
    private Long idCategory;
    private double unitValue;
    private Long preparationTime;
    private Date dateInclusion;
    private Timestamp timestamp;

    public Product() {
    }

    public Product(String productName, Long idCategory, double unitValue, Long preparationTime) {
        this.productName = productName;
        this.idCategory = idCategory;
        this.unitValue = unitValue;
        this.preparationTime = preparationTime;
        this.dateInclusion = new Date(System.currentTimeMillis());
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
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

    public double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(double unitValue) {
        this.unitValue = unitValue;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Date getDateInclusion() {
        return dateInclusion;
    }

    public void setDateInclusion(Date dateInclusion) {
        this.dateInclusion = dateInclusion;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.unitValue, unitValue) == 0 && productName.equals(product.productName) && idCategory.equals(product.idCategory) && preparationTime.equals(product.preparationTime) && dateInclusion.equals(product.dateInclusion) && timestamp.equals(product.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, idCategory, unitValue, preparationTime, dateInclusion, timestamp);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", idCategory=" + idCategory +
                ", unitValue=" + unitValue +
                ", preparationTime=" + preparationTime +
                ", dateInclusion=" + dateInclusion +
                ", timestamp=" + timestamp +
                '}';
    }
}