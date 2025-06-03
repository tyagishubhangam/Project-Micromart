package com.micromart.ProductMicroservice.dto;

import lombok.Data;

@Data
public class ProductAddRequest {

    private String productName;
    private String productDescription;
    private String categoryId;
    private double price;
    private int quantity;
    private String image;
}
