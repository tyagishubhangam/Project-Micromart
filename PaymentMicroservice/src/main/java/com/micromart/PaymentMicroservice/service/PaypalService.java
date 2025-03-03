package com.micromart.PaymentMicroservice.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelURL,
            String successURL
    ) throws PayPalRESTException;

    public Payment executePayment(String paymentID, String payerId) throws PayPalRESTException;
}
