package com.store.soattechchallenge.Product.infrastructure.adapters.in.dto;

public class OrderPostUpResponseDTO {
    private Long orderId;

    public OrderPostUpResponseDTO() {
    }

    public OrderPostUpResponseDTO(Long orderId) {
        this.orderId = orderId;
    }

    public static OrderPostUpResponseDTO builder() {
        return new OrderPostUpResponseDTO();
    }

    public Long getMessage() {
        return orderId;
    }

    public void setMessage(Long message) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPostUpResponseDTO that = (OrderPostUpResponseDTO) o;

        return orderId != null ? orderId.equals(that.orderId) : that.orderId == null;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductUpdateResponseDTO{" +
                "message='" + orderId + '\'' +
                '}';
    }
}