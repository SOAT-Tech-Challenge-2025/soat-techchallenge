package com.store.soattechchallenge.Product.infrastructure.adapters.in.dto;

public class ProductPostUpResponseDTO {
    private String message;

    public ProductPostUpResponseDTO() {
    }

    public ProductPostUpResponseDTO(String message) {
        this.message = message;
    }

    public static ProductPostUpResponseDTO builder() {
        return new ProductPostUpResponseDTO();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPostUpResponseDTO that = (ProductPostUpResponseDTO) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductUpdateResponseDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}