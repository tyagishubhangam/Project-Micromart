package com.micromart.CartMicroservice.Clients;

import com.micromart.CartMicroservice.external.Products.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ProductMicroservice", url = "${ProductMicroservice.url}")
public interface ProductMicroserviceClient {
    @GetMapping("/get/{id}")
    Product getProduct(@PathVariable("id") String id);
}
