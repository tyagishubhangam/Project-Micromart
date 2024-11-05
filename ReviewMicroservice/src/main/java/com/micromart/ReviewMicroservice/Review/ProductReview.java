package com.micromart.ReviewMicroservice.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductReview {
    @Id
    private Long id;
    private Long productId;
    private double rating;
    private String description;

    public ProductReview(Long id, Long productId, double rating, String description) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.description = description;
    }

    public ProductReview() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
