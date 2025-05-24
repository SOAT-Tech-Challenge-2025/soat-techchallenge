package com.store.soattechchallenge.Order.infrastructure.adapters.in.dto;

public class ProductRequest {
    private Long productId;
    private Integer quantity;
    private Double vlUnitProduct;

    public ProductRequest() {
    }


    public ProductRequest(Long productId,Integer quantity, Double vlUnitProduct) {
        this.productId = productId;
        this.quantity = quantity;
        this.vlUnitProduct = vlUnitProduct;
    }

    public Long getProductId() {
        return productId;
    }


    public Double getVlUnitProduct() {
        return vlUnitProduct;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setVlUnitProduct(Double vlUnitProduct) {
        this.vlUnitProduct = vlUnitProduct;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
