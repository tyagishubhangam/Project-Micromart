package com.micromart.OrderMicroservice.mappers;

import com.micromart.OrderMicroservice.dtos.OrderRequestDto;
import com.micromart.OrderMicroservice.dtos.OrderResponseDto;
import com.micromart.OrderMicroservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setUserId(orderRequestDto.getUserId());
        order.setOrderItems(orderRequestDto.getOrderItems());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setPaymentId(orderRequestDto.getPaymentId());
        return order;
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderedAt().toString())
                .orderStatus(order.getOrderStatus())
                .orderItems(order.getOrderItems())
                .paymentId(order.getPaymentId())
                .totalPrice(order.getTotalAmount())
                .userId(order.getUserId())
                .build();

    }
}
