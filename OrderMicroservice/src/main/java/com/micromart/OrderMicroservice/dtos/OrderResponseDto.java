package com.micromart.OrderMicroservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private String orderId;
    private String orderStatus;
    private String orderDate;
    private String userId;
    private List<OrderItem> orderItems;
    private String paymentId;
    private double totalPrice;
}
