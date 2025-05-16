package com.micromart.CartMicroservice.CartService;

import com.micromart.CartMicroservice.Cart.TestDisplayCart;

public interface TestCartService {
    public void addToCart(long productId, long userId, int quantity);
    public TestDisplayCart getTestCart(long userId);
    public boolean removeFromCart(long productId, long userId);
    public boolean updateQuantity(long productId, long userId, int quantity);
    public boolean deleteCart(long userId);
}
