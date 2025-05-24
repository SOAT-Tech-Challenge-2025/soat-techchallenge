package com.store.soattechchallenge.Order.infrastructure.adapters.in.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class OrderGetResponseDTO {
    private String orderId;
    private Double totalOrder;
    private Integer minute;
    private String clientId;
    private Timestamp timestamp;
    private List<ProductRequest> products;

    // Construtor vazio
    public OrderGetResponseDTO() {
    }

    // Construtor cheio
    public OrderGetResponseDTO(String orderId, Double totalOrder, Integer minute, String clientId, List<ProductRequest> products, Timestamp timestamp) {
        this.orderId = orderId;
        this.totalOrder = totalOrder;
        this.minute = minute;
        this.clientId = clientId;
        this.products = products;
        this.timestamp = timestamp;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTotalOrder() {
        return totalOrder;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getClientId() {
        return clientId;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    // toString
    @Override
    public String toString() {
        return "OrderGetResponseDTO{" +
                "orderId=" + orderId +
                ", totalOrder=" + totalOrder +
                ", minute=" + minute +
                ", clientId=" + clientId +
                ", products=" + products +
                ", timestamp=" + timestamp +
                '}';
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + totalOrder.hashCode();
        result = 31 * result + minute.hashCode();
        result = 31 * result + clientId.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + products.hashCode();
        return result;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        OrderGetResponseDTO that = (OrderGetResponseDTO) obj;

        if (!orderId.equals(that.orderId)) return false;
        if (!totalOrder.equals(that.totalOrder)) return false;
        if (!minute.equals(that.minute)) return false;
        if (!clientId.equals(that.clientId)) return false;
        if (!timestamp.equals(that.timestamp)) return false;
        return products.equals(that.products);
    }
}