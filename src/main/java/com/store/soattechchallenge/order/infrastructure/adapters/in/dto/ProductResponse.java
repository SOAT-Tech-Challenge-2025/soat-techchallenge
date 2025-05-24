package com.store.soattechchallenge.order.infrastructure.adapters.in.dto;

public class ProductResponse {
    private Long productId;
    private Integer productQt;
    private Double vlQtProduct;

    // Construtor vazio
    public ProductResponse() {
    }

    // Construtor cheio
    public ProductResponse(Long productId, Integer productQt, Double vlQtProduct) {
        this.productId = productId;
        this.productQt = productQt;
        this.vlQtProduct = vlQtProduct;
    }

    // Getters
    public Long getProductId() {
        return productId;
    }

    public Integer getProductQt() {
        return productQt;
    }

    public Double getVlQtProduct() {
        return vlQtProduct;
    }

    // Setters
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductQt(Integer productQt) {
        this.productQt = productQt;
    }

    public void setVlQtProduct(Double vlQtProduct) {
        this.vlQtProduct = vlQtProduct;
    }

    // toString
    @Override
    public String toString() {
        return "ProductResponse{" +
                "productId=" + productId +
                ", productQt=" + productQt +
                ", vlQtProduct=" + vlQtProduct +
                '}';
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + productQt.hashCode();
        result = 31 * result + vlQtProduct.hashCode();
        return result;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ProductResponse that = (ProductResponse) obj;

        if (!productId.equals(that.productId)) return false;
        if (!productQt.equals(that.productQt)) return false;
        return vlQtProduct.equals(that.vlQtProduct);
    }
}