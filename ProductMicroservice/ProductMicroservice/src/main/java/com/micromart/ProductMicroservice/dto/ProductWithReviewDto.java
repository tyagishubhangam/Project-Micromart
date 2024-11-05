package com.micromart.ProductMicroservice.dto;

import com.micromart.ProductMicroservice.external.Review;

import java.util.List;

public class ProductWithReviewDto {

    private Long id;
    private String productName;
    private String productDescription;
    //TODO: Add Category Name
//    private String categoryName;
    private double price;
    private int quantity;
    private String image;

    private List<Review> reviews;

    public ProductWithReviewDto() {
    }

    public ProductWithReviewDto(Long id, String productName, String productDescription, double price, int quantity, String image, List<Review> reviews) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
