package com.store.soattechchallenge.Order.application.service;

import com.store.soattechchallenge.Order.application.usecases.OrderUseCases;
import com.store.soattechchallenge.Order.domain.model.Order;
import com.store.soattechchallenge.Order.domain.model.OrderProduct;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderPostResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.utils.OrderUtils;
import com.store.soattechchallenge.Order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderUseCases {

    public final OrderRepositoryImpl adaptersRepository;

    public OrderServiceImpl(OrderRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public OrderPostResponseDTO saveOrder(OrderRequestDTO product) {
        String orderId = adaptersRepository.orderId();
        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(product.getProducts(), orderId);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(orderId, totalOrder, product.getMinute(), product.getClientId(), orderProducts);
        OrderPostResponseDTO responseDTO = new OrderPostResponseDTO();
        try {
            adaptersRepository.save(orderRequestModelModel);
            responseDTO.setOrderId(orderId);
        }catch (Exception e) {
            throw new RuntimeException("Error saving Product: " + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public Page<OrderGetResponseDTO> getAllOrders(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new RuntimeException("Error getting all categories: " + e.getMessage());
        }
    }

    @Override
    public Optional<OrderGetResponseDTO> getOrdeById(String id) {
        return adaptersRepository.findOrderById(id);
    }



}

