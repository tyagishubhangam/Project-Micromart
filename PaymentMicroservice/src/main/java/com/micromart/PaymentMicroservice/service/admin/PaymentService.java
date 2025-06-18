package com.micromart.PaymentMicroservice.service.admin;

import com.micromart.PaymentMicroservice.dtos.OrderRequestDto;
import com.micromart.PaymentMicroservice.dtos.ResponseDto;
import com.micromart.PaymentMicroservice.model.PaymentInfo;
import com.paypal.api.payments.Payment;

import java.util.Optional;

public interface PaymentService {
    public ResponseDto getAllTransactions();
    public ResponseDto addTransaction(Payment payment, OrderRequestDto orderRequestDto);

    public Optional<PaymentInfo> getUserTransactions(String userId);


}
