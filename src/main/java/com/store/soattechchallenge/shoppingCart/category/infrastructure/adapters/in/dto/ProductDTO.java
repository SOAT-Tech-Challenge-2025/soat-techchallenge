package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ProductDTO {
    private Long productId;
    private String nameProduct;
    private BigDecimal unitPrice;
    private Integer preparationTime;
    private Date dtInclusion;

    public ProductDTO() {}

    public ProductDTO(Long productId, String nameProduct, BigDecimal unitPrice, Integer preparationTime, Date dtInclusion) {
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.unitPrice = unitPrice;
        this.preparationTime = preparationTime;
        this.dtInclusion = dtInclusion;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Date getDtInclusion() {
        return dtInclusion;
    }

    public void setDtInclusion(Date dtInclusion) {
        this.dtInclusion = dtInclusion;
    }
}