package com.micromart.ReviewMicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ProductMicroservice")

public interface ProductClient {
    @GetMapping("/api/micromart/product/get/{productId}")
    public String getProductById(@PathVariable("productId") String id);
}
