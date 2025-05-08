package com.micromart.ProductMicroservice.Product;

import com.micromart.ProductMicroservice.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productDescription;
//    TODO: Add Category Name
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private double price;
    private int quantity;
    private String image;

    public Product() {

    }
}
