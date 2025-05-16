package com.micromart.OrderMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private String orderId;
    private String orderStatus;
    private String orderDate;
    private String paymentId;
    private List<ProductDto> products;
    private double totalPrice;
}
