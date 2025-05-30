package com.store.soattechchallenge.order.domain.model;

public class OrderProduct {
    private String id;
    private Long productId;
    private Integer qtItem;
    private Double vlQtItem;

    public OrderProduct() {
    }

    public OrderProduct(String id, Long productId, Integer qtItem, Double vlQtItem) {
        this.id = id;
        this.productId = productId;
        this.qtItem = qtItem;
        this.vlQtItem = vlQtItem;
    }

    public String getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQtItem() {
        return qtItem;
    }

    public Double getVlQtItem() {
        return vlQtItem;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQtItem(Integer qtItem) {
        this.qtItem = qtItem;
    }

    public void setVlQtItem(Double vlQtItem) {
        this.vlQtItem = vlQtItem;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", qtItem=" + qtItem +
                ", vlQtItem=" + vlQtItem +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + qtItem.hashCode();
        result = 31 * result + vlQtItem.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        OrderProduct that = (OrderProduct) obj;

        if (!id.equals(that.id)) return false;
        if (!productId.equals(that.productId)) return false;
        if (!qtItem.equals(that.qtItem)) return false;
        return vlQtItem.equals(that.vlQtItem);
    }
}