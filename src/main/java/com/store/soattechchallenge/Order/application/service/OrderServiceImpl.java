package com.store.soattechchallenge.Order.application.service;

import com.store.soattechchallenge.Order.application.usecases.OrderUseCases;
import com.store.soattechchallenge.Order.domain.model.Order;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.ProductRequestDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderPostUpResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.out.repository.impl.OrderRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderUseCases {

    public final OrderRepositoryImpl adaptersRepository;

    public OrderServiceImpl(OrderRepositoryImpl adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    @Override
    public OrderPostUpResponseDTO saveProduct(ProductRequestDTO product) {
        Order orderRequestModelModel = new Order(product.getProductName(),product.getIdCategory(),product.getIdCategory(), product.getPreparationTime());
        OrderPostUpResponseDTO responseDTO = new OrderPostUpResponseDTO();
        try {
            adaptersRepository.save(orderRequestModelModel);
            responseDTO.setMessage("Product created successful");
        }catch (Exception e) {
            throw new RuntimeException("Error saving Product: " + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public Page<OrderGetResponseDTO> getAllProducts(Pageable pageable) {
        try {
            return adaptersRepository.findAll(pageable);
        }catch (Exception e) {
            throw new RuntimeException("Error getting all categories: " + e.getMessage());
        }
    }

    @Override
    public OrderGetResponseDTO getProductById(Long id) {
        Optional<OrderGetResponseDTO> productEntityOptional;
        try {
            productEntityOptional = adaptersRepository.findById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error getting Product: " + e.getMessage());
        }
        return productEntityOptional.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public OrderPostUpResponseDTO updateProduct(Long id, ProductRequestDTO product) {
        Order Order = new Order(product.getProductName(),product.getIdCategory(),product.getIdCategory(), product.getPreparationTime());
        OrderPostUpResponseDTO OrderPostUpResponseDTO;
        try {
            OrderPostUpResponseDTO = adaptersRepository.update(Order,id);
        }catch (Exception e) {
            throw new RuntimeException("Error updating Product: " + e.getMessage());
        }
        return OrderPostUpResponseDTO;
    }

    @Override
    public OrderPostUpResponseDTO deleteProduct(Long id) {
        try {
           return adaptersRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting Product: " + e.getMessage());
        }
    }
}