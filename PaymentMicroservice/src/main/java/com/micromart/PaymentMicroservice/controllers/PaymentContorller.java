package com.micromart.PaymentMicroservice.controllers;

import com.micromart.PaymentMicroservice.dtos.PaymentDto;
import com.micromart.PaymentMicroservice.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.regex.PatternSyntaxException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/micromart/payment")
public class PaymentContorller {

    private final  PaypalService paypalService ;


    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/create")
    public RedirectView createPayment(@RequestParam double amount, @RequestParam String description) {

        try{

            String cancelURL = "http://localhost:8190/api/micromart/payment/cancel";
            String returnURL = "http://localhost:8190/api/micromart/payment/success";
            Payment payment = paypalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    description,
                    cancelURL,
                    returnURL
            );

            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        }catch (PatternSyntaxException | PayPalRESTException e){
            log.error(e.getMessage());


        }
            return new RedirectView("/error");

    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try{

            Payment payment = paypalService.executePayment(paymentId,payerId);
            if (payment.getState().equals("approved")) {
                System.out.println(payment.toString());
                return new ResponseEntity<>(payment.toString(), HttpStatus.OK);
            }
        }catch (PayPalRESTException e){
            log.error(e.getMessage());
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( HttpStatus.OK);
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
