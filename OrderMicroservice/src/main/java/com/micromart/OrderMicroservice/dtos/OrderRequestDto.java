package com.micromart.OrderMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestDto {
    private String userId;
    private List<OrderItem> orderItems;
    private double totalAmount;

    private String paymentId;
}
