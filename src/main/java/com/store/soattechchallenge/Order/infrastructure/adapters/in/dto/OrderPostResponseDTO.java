package com.store.soattechchallenge.Order.infrastructure.adapters.in.dto;

public class OrderPostResponseDTO {
    private String orderId;

    public OrderPostResponseDTO() {
    }

    public OrderPostResponseDTO(String orderId) {
        this.orderId = orderId;
    }

    public static OrderPostResponseDTO builder() {
        return new OrderPostResponseDTO();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPostResponseDTO that = (OrderPostResponseDTO) o;

        return orderId != null ? orderId.equals(that.orderId) : that.orderId == null;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductUpdateResponseDTO{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}