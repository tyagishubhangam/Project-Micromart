package com.micromart.PaymentMicroservice.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class PaymentDto {
    private double totalAmount;
    private String currency;
    private String intent;
    private String description;
 }
