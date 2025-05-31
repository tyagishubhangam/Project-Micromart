package com.micromart.ReviewMicroservice.ReviewService;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.dtos.ReviewRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewServices {
    public ProductReview addReview(String userId,ReviewRequestDto productReview);
    public List<ProductReview> getAllReviews();
    public ProductReview getReviewById(String id);
    public List<ProductReview> getReviewsByProductId(String productId);
    public boolean deleteReviewById(String id);
    public boolean updateReview(ProductReview productReview);

}
