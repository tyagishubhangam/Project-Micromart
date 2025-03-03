package com.micromart.PaymentMicroservice.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
//import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
//@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {
    private final APIContext apiContext;

    PaypalServiceImpl(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    @Override
    public Payment createPayment(Double total, String currency, String method, String intent, String description, String cancelURL, String successURL) throws PayPalRESTException {

        //Setting Up the amount object which will hold our total along with the currency of payment
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.US,"%.2f", total));

        //Setting up the Transaction Object which is used to specify our description of transaction ie for what we are doing this transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setTransactions(transactions);
        payment.setPayer(payer);
        payment.setIntent(intent);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelURL);
        redirectUrls.setReturnUrl(successURL);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);


    }

    @Override
    public Payment executePayment(String paymentID, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentID);

        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        return payment.execute(apiContext, execution);
    }
}
