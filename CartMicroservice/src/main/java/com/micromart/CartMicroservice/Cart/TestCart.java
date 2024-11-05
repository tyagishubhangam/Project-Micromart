package com.micromart.CartMicroservice.Cart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestCart {
    @Id
    private long id;
    private long productId;
    private int quantity;
    private long userId;

    public TestCart(long id, long productId, int quantity, long userId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public TestCart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
