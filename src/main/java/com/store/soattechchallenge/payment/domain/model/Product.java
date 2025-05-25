package com.store.soattechchallenge.payment.domain.model;

public class Product {
    private String name;
    private String category;
    private Double unitPrice;
    private Integer quantity;

    public Product(String name, String category, Double unitPrice, Integer quantity) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalValue() {
        return unitPrice * quantity;
    }
}
