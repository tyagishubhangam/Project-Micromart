package com.micromart.ReviewMicroservice.ReviewService;

import com.micromart.ReviewMicroservice.Repositories.ReviewRepo;
import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.clients.ProductClient;
import com.micromart.ReviewMicroservice.dtos.ReviewRequestDto;
import com.micromart.ReviewMicroservice.mappers.ReviewMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewServices{
    private final ReviewRepo reviewRepo;
    private final ProductClient productClient;
    private final ReviewMapper reviewMapper;


    @Override
    public ProductReview addReview(ReviewRequestDto reviewRequestDto) {
        try{
            if(productClient.getProductById(Long.parseLong(reviewRequestDto.getProductId())) == null) {
                return null;
            }
            ProductReview review = reviewMapper.mapToProductReview(reviewRequestDto);

            return reviewRepo.save(review);

        }catch(FeignException.NotFound e){
            log.error(e.getMessage());
            return null;
        }


    }

    @Override
    public List<ProductReview> getAllReviews() {
        return reviewRepo.findAll();
    }

    @Override
    public ProductReview getReviewById(String id) {
        Optional<ProductReview> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            return review.get();
        }
            return null;

    }

    @Override
    public List<ProductReview> getReviewsByProductId(String productId) {
        return reviewRepo.findByProductId(productId);
    }

    @Override
    public boolean deleteReviewById(String id) {
        Optional<ProductReview> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            reviewRepo.delete(review.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReview(ProductReview productReview) {
        Optional<ProductReview> review = reviewRepo.findById(productReview.getId());
        if (review.isPresent()) {
            reviewRepo.save(productReview);
            return true;
        }
        return false;
    }
}
