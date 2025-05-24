package com.store.soattechchallenge.order.domain.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private Long id;
    private double totalAmountOrder;
    private Long minute;
    private UUID clientId;
    private Timestamp timestamp;

    // Construtor vazio
    public Order() {
    }

    // Construtor cheio
    public Order(Long id, double totalAmountOrder, Long minute, UUID clientId) {
        this.id = id;
        this.totalAmountOrder = totalAmountOrder;
        this.minute = minute;
        this.clientId = clientId;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public double getTotalAmountOrder() {
        return totalAmountOrder;
    }

    public Long getMinute() {
        return minute;
    }

    public UUID getClientId() {
        return clientId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalAmountOrder(double totalAmountOrder) {
        this.totalAmountOrder = totalAmountOrder;
    }

    public void setMinute(Long minute) {
        this.minute = minute;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // toString
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmountOrder=" + totalAmountOrder +
                ", minute=" + minute +
                ", clientId=" + clientId +
                ", timestamp=" + timestamp +
                '}';
    }

    // hashCode
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + Double.hashCode(totalAmountOrder);
        result = 31 * result + minute.hashCode();
        result = 31 * result + clientId.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order order = (Order) obj;

        if (!id.equals(order.id)) return false;
        if (Double.compare(order.totalAmountOrder, totalAmountOrder) != 0) return false;
        if (!minute.equals(order.minute)) return false;
        if (!clientId.equals(order.clientId)) return false;
        return timestamp.equals(order.timestamp);
    }

}