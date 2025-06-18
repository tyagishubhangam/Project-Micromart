package com.micromart.PaymentMicroservice.service.admin;

import com.micromart.PaymentMicroservice.clients.CartClient;
import com.micromart.PaymentMicroservice.clients.OrderClient;
import com.micromart.PaymentMicroservice.dtos.OrderRequestDto;
import com.micromart.PaymentMicroservice.dtos.ResponseDto;
import com.micromart.PaymentMicroservice.mappers.PaymentInfoMapper;
import com.micromart.PaymentMicroservice.model.PaymentInfo;
import com.micromart.PaymentMicroservice.repository.PaymentRepo;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentInfoMapper paymentInfoMapper;
    public final PaymentRepo paymentRepo;
    private final OrderClient orderClient;
    private final CartClient cartClient;

    @Override
    public ResponseDto getAllTransactions() {
        return new ResponseDto("Success",paymentRepo.findAll());
    }

    @Override
    public ResponseDto addTransaction(Payment payment, OrderRequestDto orderRequestDto) {
        try{
            PaymentInfo paymentInfo = paymentInfoMapper.mapToPaymentInfo(payment,orderRequestDto.getUserId());
            paymentRepo.save(paymentInfo);
            orderRequestDto.setPaymentId(payment.getId());
            orderClient.createOrder(orderRequestDto);
            cartClient.deleteCart(orderRequestDto.getUserId());
            return new ResponseDto("Payment Added Successfully",paymentInfo);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseDto(e.getMessage(),null);
        }

    }

    @Override
    public Optional<PaymentInfo> getUserTransactions(String userId) {
        return paymentRepo.findByUserId(userId);

    }
}
