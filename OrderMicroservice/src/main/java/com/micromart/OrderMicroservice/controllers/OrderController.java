package com.micromart.OrderMicroservice.controllers;

import com.micromart.OrderMicroservice.dtos.OrderInfo;
import com.micromart.OrderMicroservice.dtos.OrderRequestDto;
import com.micromart.OrderMicroservice.dtos.OrderResponseDto;
import com.micromart.OrderMicroservice.dtos.ResponseDto;
import com.micromart.OrderMicroservice.exceptions.OrderNotFoundException;
import com.micromart.OrderMicroservice.models.Order;
import com.micromart.OrderMicroservice.services.OrderService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/micromart/order")
public class OrderController {
    public final OrderService orderService;
    @Hidden
    @GetMapping("/")
    public RedirectView swagger() {
        return new RedirectView("/swagger-ui.html");
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try{
           return ResponseEntity.status(201)
                   .body(ResponseDto.builder()
                           .message ("Order placed successfully")
                           .data(orderService.placeOrder(orderRequestDto))
                           .build());

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .message(e.getMessage())
                            .build());
        }

    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseDto> getAllOrders() {
        try{
            List<OrderResponseDto> allOrders = orderService.getAllOrders();
            return ResponseEntity.status(200).body(ResponseDto.builder()
                    .message("Success")
                    .data(allOrders)
                    .build());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(ResponseDto.builder().message(e.getMessage()).build());
        }

    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseDto> getOrder(@PathVariable String orderId) {
        OrderInfo orderInfo = orderService.getOrderInfo(orderId);
        if (orderInfo == null) {
            return ResponseEntity.status(404).body(ResponseDto.builder()
                    .data(orderInfo).message("Order not found").build());
        }

        return ResponseEntity.status(200).body(ResponseDto.builder()
                .message("Success").data(orderInfo).build());
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<ResponseDto> getUserOrders(@PathVariable("userId") String userId) {
        try {
        List<OrderResponseDto> userOrders = orderService.getOrdersByUserId(userId);
        if(userOrders.isEmpty()){
            log.warn(String.format("Orders for useId %s not found", userId));
            return ResponseEntity.status(404).body(ResponseDto.builder()
                    .message(String.format("Orders for useId %s not found", userId))
                    .build());
        }
        return ResponseEntity.status(200).body(
                ResponseDto.builder()
                        .message("Success")
                        .data(userOrders)
                        .build()
        );
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(ResponseDto.builder().message(e.getMessage()).build());
        }
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ResponseDto> updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        if(newStatus == null || newStatus.isEmpty()){
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message("Status must be provided")
                            .build());
        }
        try{
            OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId,newStatus);
            return ResponseEntity.status(200).body(
                    ResponseDto.builder()
                            .message("Status Updated Successfully")
                            .data(orderResponseDto)
                            .build()
            );
        }catch (OrderNotFoundException e){
            log.warn("Order Not Found with orderId: "+orderId);
            return ResponseEntity.status(404).body(
                    ResponseDto.builder().message(e.getMessage()).build()
            );
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(ResponseDto.builder().message(e.getMessage()).build());
        }

    }
}
