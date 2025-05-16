package com.micromart.PaymentMicroservice.controllers;

import com.micromart.PaymentMicroservice.clients.OrderClient;
import com.micromart.PaymentMicroservice.dtos.OrderRequestDto;
import com.micromart.PaymentMicroservice.dtos.ResponseDto;
import com.micromart.PaymentMicroservice.model.PaymentInfo;
import com.micromart.PaymentMicroservice.service.PaypalService;
import com.micromart.PaymentMicroservice.service.admin.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Map;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/micromart/payment")
public class PaymentController {
    @Value("${Frontend.URL}")
    String baseReturnURL;
    public final PaymentService adminServices;

    private final  PaypalService paypalService ;


    @GetMapping("/")
    public RedirectView home() {

        return new RedirectView("/swagger-ui.html");
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(adminServices.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/getByUserId")
    public ResponseEntity<ResponseDto> getByUserId(@RequestParam("userId") String userId) {
        Optional<PaymentInfo> userPayments = adminServices.getUserTransactions(userId);
        if (userPayments.isPresent()) {
            return ResponseEntity.status(200).body(new ResponseDto("Success", userPayments.get()));
        }
        return ResponseEntity.status(404).body(new ResponseDto("No Payments Found",userPayments));

    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(
            @RequestParam double amount,
            @RequestParam String description) {

        try {
            String returnURL = baseReturnURL + "/success";
            String cancelURL = baseReturnURL + "/cancel";

            Payment payment = paypalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    description,
                    cancelURL,
                    returnURL
            );

            for (Links links : payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {

                    return ResponseEntity.ok(Map.of("approvalUrl", links.getHref()));
                }
            }

            // In case approval_url is not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Approval URL not found in PayPal response"));

        } catch (PayPalRESTException | PatternSyntaxException e) {
            log.error("Error while creating PayPal payment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create PayPal payment"));
        }
    }


    @PostMapping("/success")
    public ResponseEntity<?> handleSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("token") String token, @RequestBody OrderRequestDto orderRequestDto) {

        try{
            Payment payment = paypalService.executePayment(paymentId,payerId);

            if (payment.getState().equals("approved")) {
            ResponseDto responseDto = adminServices.addTransaction(payment,orderRequestDto);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }


        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto("error", "Payment failed"));
    }

    @GetMapping("/cancel")
    public String cancel(){
        return "Canceled";
    }

    @GetMapping("/error")
    public String error(){
        return "Error";
    }
}
