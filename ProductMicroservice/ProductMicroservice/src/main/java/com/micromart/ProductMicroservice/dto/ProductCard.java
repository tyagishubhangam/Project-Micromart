package com.micromart.ProductMicroservice.dto;

import lombok.Data;

@Data
public class ProductCard {
    private String id;
    private String name;
    private double rating;
    private double price;
    private String image;
}
