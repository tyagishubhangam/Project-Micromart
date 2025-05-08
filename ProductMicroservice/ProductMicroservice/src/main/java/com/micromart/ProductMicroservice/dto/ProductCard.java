package com.micromart.ProductMicroservice.dto;

import lombok.Data;

@Data
public class ProductCard {
    private long id;
    private String name;
    private String rating;
    private double price;
    private String image;
}
