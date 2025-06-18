package com.micromart.OrderMicroservice.services;

import com.micromart.OrderMicroservice.dtos.*;
import com.micromart.OrderMicroservice.exceptions.OrderNotFoundException;
import com.micromart.OrderMicroservice.mappers.OrderItemMapper;
import com.micromart.OrderMicroservice.mappers.OrderMapper;
import com.micromart.OrderMicroservice.models.Order;
import com.micromart.OrderMicroservice.repositories.OrdersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    public final OrderMapper orderMapper;
    public final OrdersRepo ordersRepo;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {

            Order order = orderMapper.toEntity(orderRequestDto);
            Order orderEntity = ordersRepo.save(order);
            OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(orderEntity);
            return orderResponseDto;

    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> allOrders = ordersRepo.findAll();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : allOrders) {
            orderResponseDtos.add(orderMapper.toOrderResponseDto(order));
        }
        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(String userId) {
        List<Order> orders = ordersRepo.findByUserId(userId);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : orders) {
            orderResponseDtos.add(orderMapper.toOrderResponseDto(order));
        }
        return orderResponseDtos;
    }

    @Override
    public OrderResponseDto updateOrderStatus(String orderId, String newStatus) {
        Order order = ordersRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found with orderId: "+orderId));

        order.setOrderStatus(newStatus);
        Order orderEntity = ordersRepo.save(order);
        return orderMapper.toOrderResponseDto(orderEntity);
    }

    @Override
    public OrderInfo getOrderInfo(String orderId) {
        Optional<Order> order = ordersRepo.findById(orderId);

        if (order.isPresent()) {
            Order orderEntity = order.get();
            List<OrderItem> orderItems= orderEntity.getOrderItems();
            List<ProductDto> productDtos = orderItemMapper.mapToProductDto(orderEntity);
            OrderInfo orderInfo = OrderInfo.builder()
                    .orderId(orderId)
                    .orderStatus(orderEntity.getOrderStatus())
                    .orderDate(orderEntity.getOrderedAt().toString())
                    .paymentId(orderEntity.getPaymentId())
                    .products(productDtos)
                    .totalPrice(orderEntity.getTotalAmount())
                .build();
            return orderInfo;
        }
        return null;
    }
}
