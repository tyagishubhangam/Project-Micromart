package com.micromart.CartMicroservice.Clients;

import com.micromart.CartMicroservice.external.Products.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "UserMicroservice", url = "${UserMicroservice.url}")
public interface UserMicroserviceClient {
    @GetMapping("/get")
    public User getUser(@RequestParam Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
