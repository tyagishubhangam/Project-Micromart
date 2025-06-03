package com.micromart.ProductMicroservice.category;

import com.micromart.ProductMicroservice.Product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Category {
    @Id
    private String id;
    @PrePersist
    public void prePersist(){
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Column(unique = true)
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
