package com.micromart.ReviewMicroservice.Repositories;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<ProductReview, Long> {
    public List<ProductReview> findByProductId(long productId);
}
