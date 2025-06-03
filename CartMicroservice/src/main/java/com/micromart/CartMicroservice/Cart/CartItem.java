package com.micromart.CartMicroservice.Cart;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(
        name = "cart_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "productId"})
)

public class CartItem {
    @Id
    private String id;
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
    private String productId;
    private int quantity;
    private String userId;




}
