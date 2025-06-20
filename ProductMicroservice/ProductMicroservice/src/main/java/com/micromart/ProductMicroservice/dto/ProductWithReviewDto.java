package com.micromart.ProductMicroservice.dto;

import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.external.Review;
import lombok.Data;

import java.util.List;
@Data
public class ProductWithReviewDto {

    private String id;
    private String productName;
    private String productDescription;
    private String category;
    private double rating;
    private double price;
    private int quantity;
    private String image;

    private List<Review> reviews;








}
