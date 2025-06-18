package com.micromart.OrderMicroservice.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductClientResponse {
    private String id;
    private String productName;
//    private String productDescription;
    private String category;
//    private double price;
    private String image;
}
