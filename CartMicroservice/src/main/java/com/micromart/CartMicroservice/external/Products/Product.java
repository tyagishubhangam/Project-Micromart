package com.micromart.CartMicroservice.external.Products;

import org.springframework.stereotype.Component;

@Component
public class Product {
    private Long id;
    private String productName;
    private String productDescription;
    //TODO: Add Category Name
//    private String categoryName;
    private double price;
//    private int quantity;
    private String image;

    public Product(Long id, String productName, String productDescription, double price, String image) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
//        this.quantity = quantity;
        this.image = image;
    }

    public Product() {
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

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
//                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                '}';
    }
}
