package com.micromart.ReviewMicroservice.Controllers;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.ReviewService.ReviewServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/micromart/review")
public class ReviewController {
    ReviewServices reviewServices;
    public ReviewController(ReviewServices reviewServices) {
        this.reviewServices = reviewServices;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductReview>> getAllReviews() {
        List<ProductReview> productReviews = reviewServices.getAllReviews();
        return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }
    @GetMapping("/getReviews/{productId}")
    public ResponseEntity<List<ProductReview>> getReviewsByProductId(@PathVariable("productId") long productId) {
       List<ProductReview> productReviews = reviewServices.getReviewsByProductId(productId);
       if ( productReviews.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody ProductReview productReview) {
        if(reviewServices.addReview(productReview)) {
            return new ResponseEntity<>("Successfully Added Review", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to Add Review: Product Not Found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") long id) {
        boolean isDeleted = reviewServices.deleteReviewById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Successfully Deleted Review", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to Delete Review", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductReview> getReviewById(@PathVariable("id") long id) {
        ProductReview productReview = reviewServices.getReviewById(id);
        if (productReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productReview, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateReview( @RequestBody ProductReview productReview) {
        boolean isUpdated = reviewServices.updateReview(productReview);
        if (isUpdated) {
            return new ResponseEntity<>("Successfully Updated Review", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to Update Review", HttpStatus.NOT_FOUND);
    }

}
