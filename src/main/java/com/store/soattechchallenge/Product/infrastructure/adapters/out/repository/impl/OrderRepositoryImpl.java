package com.store.soattechchallenge.Product.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.Product.domain.model.Order;
import com.store.soattechchallenge.Product.domain.repository.OrderRepository;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.in.dto.OrderPostUpResponseDTO;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.entity.OrderEntity;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.mappers.OrderMapper;
import com.store.soattechchallenge.Product.infrastructure.adapters.out.repository.OrderAdaptersGetRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderAdaptersGetRepository repository;
    public final OrderMapper mapper;

    public OrderRepositoryImpl(OrderAdaptersGetRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<OrderGetResponseDTO> findById(Long id) {
        return mapper.modelToProductGetResponseDTO(repository.findById(id));
    }

    @Override
    public Page<OrderGetResponseDTO> findAll(Pageable pageable) {
        return mapper.modelToProductGetResponseDTO(repository.findAll(pageable));
    }

    @Override
    public void save(Order order) {
        repository.save(mapper.productToProductEntityMap(order));
    }

    @Override
    public OrderPostUpResponseDTO update(Order order, Long id) {
        OrderEntity orderEntityMapper = mapper.productToProductUpdateMap(order,id);
        AtomicReference<Boolean> updated = new AtomicReference<>(true);
        OrderPostUpResponseDTO responseDTO = new OrderPostUpResponseDTO();
        repository.findById(id).ifPresentOrElse(
                productEntity -> {
                    repository.save(orderEntityMapper);
                },
                () -> {
                    updated.set(false);
                }
        );
        if(updated.get()) {
            responseDTO.setMessage("Product updated");
            return responseDTO;
        }
        responseDTO.setMessage("Product not updated");
        return responseDTO;
    }

    @Override
    public OrderPostUpResponseDTO deleteById(Long id) {
        Optional<OrderEntity> entity = repository.findById(id);
        OrderPostUpResponseDTO responseDTO = new OrderPostUpResponseDTO();
        if (entity.isPresent()) {
            repository.deleteById(id);
            responseDTO.setMessage("Product deleted");
        }else {
            responseDTO.setMessage("Product not exist");
        }
        return responseDTO;
    }
}
