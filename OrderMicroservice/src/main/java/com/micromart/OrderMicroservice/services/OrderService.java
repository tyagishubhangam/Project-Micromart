package com.micromart.OrderMicroservice.services;

import com.micromart.OrderMicroservice.dtos.OrderInfo;
import com.micromart.OrderMicroservice.dtos.OrderRequestDto;
import com.micromart.OrderMicroservice.dtos.OrderResponseDto;
import com.micromart.OrderMicroservice.dtos.ResponseDto;

import java.util.List;

public interface OrderService {
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);
    public List<OrderResponseDto> getAllOrders();
    public List<OrderResponseDto> getOrdersByUserId(String userId);

    OrderResponseDto updateOrderStatus(String orderId, String newStatus);
    public OrderInfo getOrderInfo(String orderId);
}
