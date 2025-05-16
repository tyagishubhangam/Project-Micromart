package com.micromart.PaymentMicroservice.mappers;

import com.micromart.PaymentMicroservice.model.PaymentInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class PaymentInfoMapper {

    public PaymentInfo mapToPaymentInfo(Payment payment,String userID) {
        PaymentInfo paymentInfo = new PaymentInfo();
        try{
            Transaction transactions = payment.getTransactions().get(0);
            paymentInfo.setUserId(userID);
            paymentInfo.setPaymentId(payment.getId());
            paymentInfo.setAmount(Double.parseDouble(transactions.getAmount().getTotal()));
            paymentInfo.setPaymentMethod(payment.getPayer().getPaymentMethod());
            paymentInfo.setPayerEmail(payment.getPayer().getPayerInfo().getEmail());
            paymentInfo.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
            paymentInfo.setPayerName(payment.getPayer().getPayerInfo().getFirstName() + " " + payment.getPayer().getPayerInfo().getLastName());
            paymentInfo.setPayerCountryCode(payment.getPayer().getPayerInfo().getCountryCode());
            paymentInfo.setCurrency(transactions.getAmount().getCurrency());
            paymentInfo.setCreatedAt(Instant.parse(payment.getCreateTime()));
            paymentInfo.setPaymentMode(transactions.getRelatedResources().get(0).getSale().getPaymentMode());
            paymentInfo.setStatus(transactions.getRelatedResources().get(0).getSale().getState());


        }catch(Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return paymentInfo;
    }
}

//
//{
//        "id": "PAYID-NARYYNQ5XP1236533346913Y",
//        "intent": "sale",
//        "payer": {
//        "payment_method": "paypal",
//        "status": "VERIFIED",
//        "payer_info": {
//        "email": "mytest189@mytest.com",
//        "first_name": "John",
//        "last_name": "Doe",
//        "payer_id": "LTZL6SXVW3CH6",
//        "country_code": "US",
//        "shipping_address": {
//        "recipient_name": "John Doe",
//        "line1": "1 Main St",
//        "city": "San Jose",
//        "country_code": "US",
//        "postal_code": "95131",
//        "state": "CA"
//        }
//        }
//        },
//        "cart": "1NT01792GY428001T",
//        "transactions": [
//        {
//        "related_resources": [
//        {
//        "sale": {
//        "id": "36U15927AU081330P",
//        "amount": {
//        "currency": "USD",
//        "total": "137900.00",
//        "details": {
//        "subtotal": "137900.00",
//        "shipping": "0.00",
//        "handling_fee": "0.00",
//        "shipping_discount": "0.00",
//        "insurance": "0.00"
//        }
//        },
//        "payment_mode": "INSTANT_TRANSFER",
//        "state": "completed",
//        "protection_eligibility": "ELIGIBLE",
//        "protection_eligibility_type": "ITEM_NOT_RECEIVED_ELIGIBLE,UNAUTHORIZED_PAYMENT_ELIGIBLE",
//        "transaction_fee": {
//        "currency": "USD",
//        "value": "6346.51"
//        },
//        "parent_payment": "PAYID-NARYYNQ5XP1236533346913Y",
//        "create_time": "2025-05-13T18:15:50Z",
//        "update_time": "2025-05-13T18:15:50Z",
//        "links": [
//        {
//        "href": "https://api.sandbox.paypal.com/v1/payments/sale/36U15927AU081330P",
//        "rel": "self",
//        "method": "GET"
//        },
//        {
//        "href": "https://api.sandbox.paypal.com/v1/payments/sale/36U15927AU081330P/refund",
//        "rel": "refund",
//        "method": "POST"
//        },
//        {
//        "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAYID-NARYYNQ5XP1236533346913Y",
//        "rel": "parent_payment",
//        "method": "GET"
//        }
//        ]
//        }
//        }
//        ],
//        "amount": {
//        "currency": "USD",
//        "total": "137900.00",
//        "details": {
//        "subtotal": "137900.00",
//        "shipping": "0.00",
//        "handling_fee": "0.00",
//        "shipping_discount": "0.00",
//        "insurance": "0.00"
//        }
//        },
//        "payee": {
//        "email": "sb-1itvk33567444@business.example.com",
//        "merchant_id": "8M4R6SPQFBE7G"
//        },
//        "description": "SALE",
//        "item_list": {
//        "shipping_address": {
//        "recipient_name": "John Doe",
//        "line1": "1 Main St",
//        "city": "San Jose",
//        "country_code": "US",
//        "postal_code": "95131",
//        "state": "CA"
//        }
//        }
//        }
//        ],
//        "failed_transactions": [],
//        "state": "approved",
//        "create_time": "2025-05-13T18:15:18Z",
//        "update_time": "2025-05-13T18:15:50Z",
//        "links": [
//        {
//        "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAYID-NARYYNQ5XP1236533346913Y",
//        "rel": "self",
//        "method": "GET"
//        }
//        ]
//        }
