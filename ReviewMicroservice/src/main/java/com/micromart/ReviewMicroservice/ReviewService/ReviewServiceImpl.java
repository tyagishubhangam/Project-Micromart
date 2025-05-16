package com.micromart.ReviewMicroservice.ReviewService;

import com.micromart.ReviewMicroservice.Repositories.ReviewRepo;
import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.clients.ProductClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewServices{
    private final ReviewRepo reviewRepo;
    private final ProductClient productClient;


    @Override
    public boolean addReview(ProductReview productReview) {
        try{
            if(productClient.getProductById(productReview.getProductId()) == null) {
                return false;
            }
            reviewRepo.save(productReview);
            return true;
        }catch(FeignException.NotFound e){
            return false;
        }


    }

    @Override
    public List<ProductReview> getAllReviews() {
        return reviewRepo.findAll();
    }

    @Override
    public ProductReview getReviewById(Long id) {
        Optional<ProductReview> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            return review.get();
        }
            return null;

    }

    @Override
    public List<ProductReview> getReviewsByProductId(Long productId) {
        return reviewRepo.findByProductId(productId);
    }

    @Override
    public boolean deleteReviewById(Long id) {
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
