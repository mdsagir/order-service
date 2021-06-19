package com.example.controller;

import com.example.entity.Order;
import com.example.entity.OrderRequest;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
