package com.micromart.CartMicroservice.dtos;

import lombok.Data;

@Data
public class AddToCartRequest {
    private long userId;
    private long productId;
    private int quantity;
}
