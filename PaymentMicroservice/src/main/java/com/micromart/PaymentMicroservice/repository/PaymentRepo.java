package com.micromart.PaymentMicroservice.repository;

import com.micromart.PaymentMicroservice.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentInfo, String> {
    public Optional<PaymentInfo> findByUserId(String userId);
}
