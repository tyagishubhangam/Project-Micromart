package com.micromart.CartMicroservice.repositories;

import com.micromart.CartMicroservice.Cart.TestCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends JpaRepository<TestCart, Long> {
    public TestCart findByUserId(long userId);
    public void deleteByProductId(long productId);
    public TestCart findByProductId(long productId);
    public TestCart findByProductIdAndUserId(long productId, long userId);
}
