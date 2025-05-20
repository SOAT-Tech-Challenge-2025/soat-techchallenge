package com.store.soattechchallenge.Product.domain.model;

public class OrderProduct {
    private Long id;
    private Long productId;
    private Integer quantityItem;
    private Double vlQtItem;

    // Construtor vazio
    public OrderProduct() {
    }

    // Construtor cheio
    public OrderProduct(Long id, Long productId, Integer quantityItem, Double vlQtItem) {
        this.id = id;
        this.productId = productId;
        this.quantityItem = quantityItem;
        this.vlQtItem = vlQtItem;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantityItem() {
        return quantityItem;
    }

    public Double getVlQtItem() {
        return vlQtItem;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantityItem(Integer quantityItem) {
        this.quantityItem = quantityItem;
    }

    public void setVlQtItem(Double vlQtItem) {
        this.vlQtItem = vlQtItem;
    }

    // toString
    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantityItem=" + quantityItem +
                ", vlQtItem=" + vlQtItem +
                '}';
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + quantityItem.hashCode();
        result = 31 * result + vlQtItem.hashCode();
        return result;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        OrderProduct that = (OrderProduct) obj;

        if (!id.equals(that.id)) return false;
        if (!productId.equals(that.productId)) return false;
        if (!quantityItem.equals(that.quantityItem)) return false;
        return vlQtItem.equals(that.vlQtItem);
    }
}