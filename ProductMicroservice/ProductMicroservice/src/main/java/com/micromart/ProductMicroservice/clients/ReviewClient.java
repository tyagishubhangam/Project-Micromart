package com.micromart.ProductMicroservice.clients;


import com.micromart.ProductMicroservice.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ReviewMicroservice", url = "${ReviewMicroservice.url}")
public interface ReviewClient {
    @GetMapping("/getReviews/{productId}")
    public List<Review> getReviews(@PathVariable("productId") String productId);
}
