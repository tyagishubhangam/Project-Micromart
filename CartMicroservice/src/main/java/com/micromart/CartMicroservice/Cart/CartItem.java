package com.micromart.CartMicroservice.Cart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
        name = "cart_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "productId"})
)

public class CartItem {
    @Id
    private long id;
    private long productId;
    private int quantity;
    private long userId;

    public CartItem(long id, long productId, int quantity, long userId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public CartItem() {
    }

}
