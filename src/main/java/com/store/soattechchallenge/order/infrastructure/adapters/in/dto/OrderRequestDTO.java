package com.store.soattechchallenge.order.infrastructure.adapters.in.dto;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OrderRequestDTO {
    private Integer minute;
    private String clientId;
    private List<ProductRequest> products;


    public OrderRequestDTO() {
    }

    public OrderRequestDTO( Integer minute, String clientId, List<ProductRequest> products) {
        this.minute = minute;
        this.clientId = clientId;
        this.products = products;
    }


    public Integer getMinute() {
        return minute;
    }

    public String getClientId() {
        return clientId;
    }

    public List<ProductRequest>  getProducts() {
        return products;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setProducts(List<ProductRequest>  products) {
        this.products = products;
    }
}