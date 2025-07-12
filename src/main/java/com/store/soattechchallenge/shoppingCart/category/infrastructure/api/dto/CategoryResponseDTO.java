package com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto;

public class CategoryResponseDTO {
    private String message;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(String message) {
        this.message = message;
    }

    public static CategoryResponseDTO builder() {
        return new CategoryResponseDTO();
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

        CategoryResponseDTO that = (CategoryResponseDTO) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CategoryUpdateResponseDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}