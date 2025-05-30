package com.store.soattechchallenge.shoppingCart.order.infrastructure.adapters.out.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JPAOrderProductId implements Serializable {
    private String id;
    @Column(name = "id_produto")
    private Long productId;

    public JPAOrderProductId() {}

    public JPAOrderProductId(String id, Long productId) {
        this.id = id;
        this.productId = productId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPAOrderProductId)) return false;
        JPAOrderProductId that = (JPAOrderProductId) o;
        return Objects.equals(id, that.id) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId);
    }
}