package com.store.soattechchallenge.order.application.service;

import com.store.soattechchallenge.order.application.usecases.OrderUseCases;
import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.domain.model.OrderProduct;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.utils.OrderUtils;
import com.store.soattechchallenge.order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderUseCases {

    public final OrderRepositoryImpl adaptersRepository;

    public OrderServiceImpl(OrderRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public Optional<OrderResponseDTO> saveOrder(OrderRequestDTO product) {
        String orderId = adaptersRepository.orderId();
        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(product.getProducts(), orderId);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(orderId, totalOrder, product.getMinute(), product.getClientId(), orderProducts);
        try {
            adaptersRepository.save(orderRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao gerar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return adaptersRepository.findOrderById(orderId);
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao buscar os pedidos",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Optional<OrderResponseDTO> getOrdeById(String id) {
        return adaptersRepository.findOrderById(id);
    }

    @Override
    public Optional<OrderResponseDTO> updateOrder(String id, OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = getOrdeById(id).orElseThrow(() -> new CustomException(
                "Pedido não encontrado",
                HttpStatus.NOT_FOUND,
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                LocalDateTime.now(),
                UUID.randomUUID()
        ));

        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(orderRequestDTO.getProducts(), id);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(id, totalOrder, orderRequestDTO.getMinute(), orderRequestDTO.getClientId(), orderProducts);

        try {
            adaptersRepository.save(orderRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return adaptersRepository.findOrderById(id);
    }

}

