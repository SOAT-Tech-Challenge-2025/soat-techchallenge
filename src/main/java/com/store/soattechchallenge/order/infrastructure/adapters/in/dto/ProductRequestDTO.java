package com.store.soattechchallenge.order.infrastructure.adapters.in.dto;

import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class ProductRequestDTO {
    private Double totalOrder;
    private Integer minute;
    private UUID clientId;
    private ProductResponse products;

    // Construtor vazio
    public ProductRequestDTO() {
    }

    // Construtor cheio
    public ProductRequestDTO(Double totalOrder, Integer minute, UUID clientId, ProductResponse products) {
        this.totalOrder = totalOrder;
        this.minute = minute;
        this.clientId = clientId;
        this.products = products;
    }

    // Getters
    public Double getTotalOrder() {
        return totalOrder;
    }

    public Integer getMinute() {
        return minute;
    }

    public UUID getClientId() {
        return clientId;
    }

    public ProductResponse getProducts() {
        return products;
    }

    // Setters
    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setProducts(ProductResponse products) {
        this.products = products;
    }

    // toString
    @Override
    public String toString() {
        return "ProductRequestDTO{" +
                "totalOrder=" + totalOrder +
                ", minute=" + minute +
                ", clientId=" + clientId +
                ", products=" + products +
                '}';
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = totalOrder.hashCode();
        result = 31 * result + minute.hashCode();
        result = 31 * result + clientId.hashCode();
        result = 31 * result + products.hashCode();
        return result;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ProductRequestDTO that = (ProductRequestDTO) obj;

        if (!totalOrder.equals(that.totalOrder)) return false;
        if (!minute.equals(that.minute)) return false;
        if (!clientId.equals(that.clientId)) return false;
        return products.equals(that.products);
    }
}