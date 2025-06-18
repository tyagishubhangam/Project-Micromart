package com.micromart.PaymentMicroservice.clients;

import com.micromart.PaymentMicroservice.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CartMicroservice",path = "/api/micromart/cart")
public interface CartClient {

    @DeleteMapping("/delete")
    ResponseDto deleteCart(@RequestParam("userId") String userId);
}
