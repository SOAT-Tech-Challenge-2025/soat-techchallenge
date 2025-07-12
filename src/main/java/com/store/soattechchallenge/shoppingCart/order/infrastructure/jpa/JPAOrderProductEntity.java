package com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_carrinho_produto")
public class JPAOrderProductEntity {
    @EmbeddedId
    private JPAOrderProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    @JoinColumn(name = "id", referencedColumnName = "id")
    private JPAOrderEntity order;

    @Column(name = "qt_item")
    private Integer qtItem;

    @Column(name = "vl_qt_item")
    private Double vlQtItem;

    public JPAOrderProductEntity() {}

    public JPAOrderProductEntity(JPAOrderProductId id, JPAOrderEntity order, Integer qtItem, Double vlQtItem) {
        this.id = id;
        this.order = order;
        this.qtItem = qtItem;
        this.vlQtItem = vlQtItem;
    }

    public JPAOrderProductId getId() {
        return id;
    }

    public void setId(JPAOrderProductId id) {
        this.id = id;
    }

    public Long getProductId() {
        return id != null ? id.getProductId() : null;
    }

    public void setProductId(Long productId) {
        if (this.id == null) this.id = new JPAOrderProductId();
        this.id.setProductId(productId);
    }

    public String getOrderId() {
        return id != null ? id.getId() : null;
    }

    public void setOrderId(String orderId) {
        if (this.id == null) this.id = new JPAOrderProductId();
        this.id.setId(orderId);
    }

    public JPAOrderEntity getOrder() {
        return order;
    }

    public void setOrder(JPAOrderEntity order) {
        this.order = order;
    }

    public Integer getQtItem() {
        return qtItem;
    }

    public void setQtItem(Integer qtItem) {
        this.qtItem = qtItem;
    }

    public Double getVlQtItem() {
        return vlQtItem;
    }

    public void setVlQtItem(Double vlQtItem) {
        this.vlQtItem = vlQtItem;
    }

    @Override
    public String toString() {
        return "JPAOrderProductEntity{" +
                "id=" + id +
                ", qtItem=" + qtItem +
                ", vlQtItem=" + vlQtItem +
                ", orderId=" + (order != null ? order.getId() : null) +
                '}';
    }
}