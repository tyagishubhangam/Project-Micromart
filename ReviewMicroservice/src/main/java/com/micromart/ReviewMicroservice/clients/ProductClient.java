package com.micromart.ReviewMicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ProductMicroservice", url = "${ProductMicroservice.url}")
public interface ProductClient {
    @GetMapping("/get")
    public String getProductById(@RequestParam("id") long id);
}
