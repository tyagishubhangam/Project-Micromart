package com.micromart.CartMicroservice.repositories;

import com.micromart.CartMicroservice.Cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//ToDo implement optional for all single entities
@Repository
public interface TestRepo extends JpaRepository<CartItem, Long> {
    public List<CartItem> findByUserId(long userId);
    public void deleteByProductId(long productId);
    public CartItem findByProductId(long productId);
    public CartItem findByProductIdAndUserId(long productId, long userId);

}
