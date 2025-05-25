package com.store.soattechchallenge.order.infrastructure.adapters.in.dto;

import java.sql.Timestamp;
import java.util.List;

public class OrderResponseDTO {
    private String orderId;
    private Double totalOrder;
    private Integer preparationTime;
    private String clientId;
    private Timestamp timestamp;
    private List<ProductResponse> products;

    // Construtor vazio
    public OrderResponseDTO() {
    }

    // Construtor cheio
    public OrderResponseDTO(String orderId, Double totalOrder, Integer preparationTime, String clientId, List<ProductResponse> products, Timestamp timestamp) {
        this.orderId = orderId;
        this.totalOrder = totalOrder;
        this.preparationTime = preparationTime;
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

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public String getClientId() {
        return clientId;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    // toString
    @Override
    public String toString() {
        return "OrderGetResponseDTO{" +
                "orderId=" + orderId +
                ", totalOrder=" + totalOrder +
                ", preparationTime=" + preparationTime +
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
        result = 31 * result + preparationTime.hashCode();
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

        OrderResponseDTO that = (OrderResponseDTO) obj;

        if (!orderId.equals(that.orderId)) return false;
        if (!totalOrder.equals(that.totalOrder)) return false;
        if (!preparationTime.equals(that.preparationTime)) return false;
        if (!clientId.equals(that.clientId)) return false;
        if (!timestamp.equals(that.timestamp)) return false;
        return products.equals(that.products);
    }
}