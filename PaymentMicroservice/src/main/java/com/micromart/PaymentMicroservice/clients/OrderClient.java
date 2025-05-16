package com.micromart.PaymentMicroservice.clients;

import com.micromart.PaymentMicroservice.dtos.OrderRequestDto;
import com.micromart.PaymentMicroservice.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "OrderMicroservice",path = "/api/micromart/order")
public interface OrderClient {

    @PostMapping("/orders")
    ResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto);
}
