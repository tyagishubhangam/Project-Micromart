package com.micromart.CartMicroservice.dtos;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String productId;
    private int quantity;
}
