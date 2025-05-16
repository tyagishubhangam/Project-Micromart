package com.micromart.CartMicroservice.external.Products;

import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class Product {
    private Long id;
    private String productName;
    private String productDescription;

    private String category;
    private double price;
//    private int quantity;
    private String image;


}
