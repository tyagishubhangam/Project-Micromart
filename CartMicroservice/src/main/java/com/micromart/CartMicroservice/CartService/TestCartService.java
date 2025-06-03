package com.micromart.CartMicroservice.CartService;

import com.micromart.CartMicroservice.Cart.TestDisplayCart;

public interface TestCartService {
    public void addToCart(String productId, String userId, int quantity);
    public TestDisplayCart getTestCart(String userId);
    public boolean removeFromCart(String productId, String userId);
    public boolean updateQuantity(String productId, String userId, int quantity);
    public boolean deleteCart(String userId);
}
