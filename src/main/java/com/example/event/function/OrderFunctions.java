package com.example.event.function;

import com.example.entity.Order;
import com.example.entity.OrderStatus;
import com.example.event.model.OrderAcceptedMessage;
import com.example.event.model.OrderDispatchedMessage;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrderFunctions {

    @Bean
    public Consumer<Order> publishOrderAcceptedEvent(StreamBridge streamBridge) {
        return order -> {
            if (!order.getStatus().equals(OrderStatus.ACCEPTED)) {
                return;
            }
            OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.getId());
            log.info("Sending order accepted event with id: " + order.getId());
            streamBridge.send("order-accepted", orderAcceptedMessage);
        };
    }

    @Bean
    public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
        return orderDispatchedMessage -> {
            log.info("The order with id " + orderDispatchedMessage.getOrderId() + " has been dispatched.");
            orderService.updateOrderStatus(orderDispatchedMessage.getOrderId(), OrderStatus.DISPATCHED);
        };
    }
}
