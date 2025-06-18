package com.micromart.ReviewMicroservice.mappers;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.dtos.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public ProductReview mapToProductReview(ReviewRequestDto productReview) {
        ProductReview review = ProductReview.builder()
                .productId(productReview.getProductId())
                .description(productReview.getDescription())
                .userFullName(productReview.getUserFullName())
                .rating(productReview.getRating())
                .build();
    return review;
    }
}
