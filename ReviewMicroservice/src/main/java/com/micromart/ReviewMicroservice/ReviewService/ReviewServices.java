package com.micromart.ReviewMicroservice.ReviewService;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewServices {
    public boolean addReview(ProductReview productReview);
    public List<ProductReview> getAllReviews();
    public ProductReview getReviewById(Long id);
    public List<ProductReview> getReviewsByProductId(Long productId);
    public boolean deleteReviewById(Long id);
    public boolean updateReview(ProductReview productReview);

}
