package com.example.service.impl;

import com.example.client.BookClient;
import com.example.entity.Book;
import com.example.entity.Order;
import com.example.entity.OrderRequest;
import com.example.entity.OrderStatus;
import com.example.repo.OrderRepository;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final BookClient bookClient;
    private final Consumer<Order> acceptedOrderConsumer;
    private final OrderRepository orderRepository;

    @Override
    public Mono<Order> submitOrder(OrderRequest orderRequest) {

        return bookClient.getBookByIsbn(orderRequest.getIsbn())
                .flatMap(book -> Mono.just(buildAcceptedOrder(book, orderRequest.getQuantity())))
                .defaultIfEmpty(buildRejectedOrder(orderRequest.getIsbn(), orderRequest.getQuantity()))
                .flatMap(orderRepository::save)
                .doOnNext(acceptedOrderConsumer);
    }

    @Override
    public Mono<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Flux<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    return order;
                })
                .flatMap(orderRepository::save)
                .subscribe();
    }

    private Order buildAcceptedOrder(Book book, int quantity) {
        return new Order(book.getIsbn(),
                book.getTitle(),
                book.getPrice(),
                quantity,
                OrderStatus.ACCEPTED);
    }

    private Order buildRejectedOrder(String isbn, int quantity) {
        return new Order(isbn, quantity, OrderStatus.REJECTED);
    }
}
