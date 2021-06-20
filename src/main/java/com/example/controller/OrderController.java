package com.example.controller;

import com.example.entity.Order;
import com.example.entity.OrderRequest;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<Order> submitOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.submitOrder(orderRequest);
    }

    @GetMapping
    public Flux<Order> getOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

}
