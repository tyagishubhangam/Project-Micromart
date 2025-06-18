package com.micromart.PaymentMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String userId;
    private List<OrderItem> orderItems;
    private double totalAmount;

    private String paymentId;
}
