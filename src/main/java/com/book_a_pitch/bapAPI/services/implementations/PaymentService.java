package com.book_a_pitch.bapAPI.services.implementations;

import com.book_a_pitch.bapAPI.common.dtos.PaymentRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;
import com.book_a_pitch.bapAPI.services.interfaces.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {
    @Value("${stripe.secretKey}")
    private String secretKey;

    @Override
    public Result<Boolean> processPayment(PaymentRequest paymentRequest) {
        try {
            // 1. Create PaymentIntent
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount((long) (paymentRequest.getPrice() * 100L)) // Convert to cents
                            .setCurrency("eur")
                            .setPaymentMethod("card")
                            .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // 2. Check status
            if ("succeeded".equals(paymentIntent.getStatus())) {
                return new Result<>(true);
            } else {
                return new Result<>("Payment failed or pending: " + paymentIntent.getStatus());
            }
        } catch (StripeException e) {
            return new Result<>("Stripe error: " + e.getMessage());
        }
    }
}
