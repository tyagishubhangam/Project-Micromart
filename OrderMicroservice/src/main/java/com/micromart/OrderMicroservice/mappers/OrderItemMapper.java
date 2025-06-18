package com.micromart.OrderMicroservice.mappers;

import com.micromart.OrderMicroservice.clients.ProductClient;
import com.micromart.OrderMicroservice.dtos.OrderItem;
import com.micromart.OrderMicroservice.dtos.ProductDto;
import com.micromart.OrderMicroservice.models.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemMapper {

    private final ProductClient productClient;

    public OrderItemMapper(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<ProductDto> mapToProductDto(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<ProductDto> productDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            ProductDto productDto = ProductDto.builder()
                    .quantity(orderItem.getQuantity())
                    .product(productClient.getProductById(orderItem.getProductId()))
                    .build();
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
