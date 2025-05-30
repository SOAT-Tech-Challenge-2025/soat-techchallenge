package com.store.soattechchallenge.shoppingCart.order.domain.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private String id;
    private Double totalAmountOrder;
    private Integer minute;
    private String clientId;
    private Timestamp timestamp;
    private List<OrderProduct> orderProducts;

    public Order() {
    }

    public Order(String id, Double totalAmountOrder, Integer minute, String clientId, List<OrderProduct> orderProducts) {
        this.id = id;
        this.totalAmountOrder = totalAmountOrder;
        this.minute = minute;
        this.clientId = clientId;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.orderProducts = orderProducts;
    }


    public String getId() {
        return id;
    }
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Double getTotalAmountOrder() {
        return totalAmountOrder;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getClientId() {
        return clientId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotalAmountOrder(Double totalAmountOrder) {
        this.totalAmountOrder = totalAmountOrder;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

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

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + Double.hashCode(totalAmountOrder);
        result = 31 * result + minute.hashCode();
        result = 31 * result + clientId.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (orderProducts != null ? orderProducts.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order order = (Order) obj;

        if (!id.equals(order.id)) return false;
        if (Double.compare(order.totalAmountOrder, totalAmountOrder) != 0) return false;
        if (!minute.equals(order.minute)) return false;
        if (orderProducts != null ? !orderProducts.equals(order.orderProducts) : order.orderProducts != null)
            return false;
        if (!clientId.equals(order.clientId)) return false;
        return timestamp.equals(order.timestamp);
    }

}