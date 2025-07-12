package com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto;

public class ProductRequest {
    private Long productId;
    private Double vlUnitProduct;
    private Integer preparationTime;

    public ProductRequest() {
    }

    public ProductRequest(Long productId, Double vlUnitProduct, Integer preparationTime) {
        this.productId = productId;
        this.vlUnitProduct = vlUnitProduct;
        this.preparationTime = preparationTime;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getVlUnitProduct() {
        return vlUnitProduct;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setVlUnitProduct(Double vlUnitProduct) {
        this.vlUnitProduct = vlUnitProduct;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
}