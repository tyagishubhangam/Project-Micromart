package com.micromart.ReviewMicroservice.Controllers;

import com.micromart.ReviewMicroservice.Review.ProductReview;
import com.micromart.ReviewMicroservice.ReviewService.ReviewServices;
import com.micromart.ReviewMicroservice.dtos.ResponseDto;
import com.micromart.ReviewMicroservice.dtos.ReviewRequestDto;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/micromart/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServices reviewServices;
    @Hidden
    @GetMapping("/")
    public RedirectView redirect() {
        return new RedirectView("/swagger-ui.html");
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductReview>> getAllReviews() {
        List<ProductReview> productReviews = reviewServices.getAllReviews();
        return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }
    @GetMapping("/getReviews/{productId}")
    public ResponseEntity<List<ProductReview>> getReviewsByProductId(@PathVariable("productId") String productId) {
       List<ProductReview> productReviews = reviewServices.getReviewsByProductId(productId);
       if ( productReviews.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }

    @PostMapping("/addReview")
    public ResponseEntity<ResponseDto> add(HttpServletRequest request , @RequestBody ReviewRequestDto productReview) {


        ProductReview review = reviewServices.addReview(productReview);
        if(review != null) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .data(review)
                    .message("Review added successfully")
                    .build(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(ResponseDto.builder()
                .message("Failed to Add Review: Error Occured")
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") String id) {
        boolean isDeleted = reviewServices.deleteReviewById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Successfully Deleted Review", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to Delete Review", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductReview> getReviewById(@PathVariable("id") String id) {
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
