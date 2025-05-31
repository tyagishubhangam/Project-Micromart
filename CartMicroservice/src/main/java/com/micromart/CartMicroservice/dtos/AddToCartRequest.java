package com.micromart.CartMicroservice.dtos;

import lombok.Data;

@Data
public class AddToCartRequest {
    private long productId;
    private int quantity;
}
