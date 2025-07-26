package com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto;

public class CategoryMessagerResponseDTO {
    private String message;

    public CategoryMessagerResponseDTO() {
    }

    public CategoryMessagerResponseDTO(String message) {
        this.message = message;
    }

    public static CategoryMessagerResponseDTO builder() {
        return new CategoryMessagerResponseDTO();
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

        CategoryMessagerResponseDTO that = (CategoryMessagerResponseDTO) o;

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