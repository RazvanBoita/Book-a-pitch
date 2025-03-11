package com.book_a_pitch.bapAPI.services.interfaces;

import com.book_a_pitch.bapAPI.common.dtos.PaymentRequest;
import com.book_a_pitch.bapAPI.common.responses.Result;

public interface IPaymentService {
    public Result<Boolean> processPayment(PaymentRequest paymentRequest);
}
