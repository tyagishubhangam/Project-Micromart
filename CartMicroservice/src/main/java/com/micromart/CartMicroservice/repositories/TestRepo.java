package com.micromart.CartMicroservice.repositories;

import com.micromart.CartMicroservice.Cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//ToDo implement optional for all single entities
@Repository
public interface TestRepo extends JpaRepository<CartItem, String> {
    public List<CartItem> findByUserId(String userId);
    public void deleteByProductId(String productId);
    public CartItem findByProductId(String productId);
    public CartItem findByProductIdAndUserId(String productId, String userId);

}
