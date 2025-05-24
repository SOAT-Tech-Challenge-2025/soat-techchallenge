package com.store.soattechchallenge.order.infrastructure.adapters.out.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tb_carrinho_pedido")
public class JPAOrderEntity {
    @Id
    private String id;

    @Column(name = "vl_total_pedido")
    private double totalAmountOrder;

    @Column(name = "minutagem")
    private Integer minute;

    @Column(name = "id_cliente")
    private String clientId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JPAOrderProductEntity> produtos;

    public JPAOrderEntity() {}

    public JPAOrderEntity(String id, double totalAmountOrder, Integer minute, String clientId, Timestamp timestamp, List<JPAOrderProductEntity> produtos) {
        this.id = id;
        this.totalAmountOrder = totalAmountOrder;
        this.minute = minute;
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.produtos = produtos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalAmountOrder() {
        return totalAmountOrder;
    }

    public void setTotalAmountOrder(double totalAmountOrder) {
        this.totalAmountOrder = totalAmountOrder;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<JPAOrderProductEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<JPAOrderProductEntity> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "JPAOrderEntity{" +
                "id='" + id + '\'' +
                ", totalAmountOrder=" + totalAmountOrder +
                ", minute=" + minute +
                ", clientId=" + clientId +
                ", timestamp=" + timestamp +
                ", produtos=" + (produtos != null ? produtos.size() : 0) +
                '}';
    }
}