package com.micromart.ProductMicroservice.external;

import lombok.Data;

@Data
public class Review {
    private String id;
    private Long productId;
    private double rating;
    private String description;


}
