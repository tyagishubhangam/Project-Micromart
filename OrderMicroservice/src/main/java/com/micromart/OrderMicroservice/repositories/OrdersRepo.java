package com.micromart.OrderMicroservice.repositories;

import com.micromart.OrderMicroservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrdersRepo extends JpaRepository<Order, String> {
    public List<Order> findByUserId(String userId);
}
