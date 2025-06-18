package com.micromart.OrderMicroservice.clients;

import com.micromart.OrderMicroservice.dtos.ProductClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductMicroservice",path = "/api/micromart/product")
public interface ProductClient {
    @GetMapping("/get/{productId}")
    public ProductClientResponse getProductById(@PathVariable String productId);
}
