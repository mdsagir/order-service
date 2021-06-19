package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Order> submitOrder(OrderRequest order);
    Mono<Order> getOrder(Long id);
    Flux<Order> getAllOrder();

}
