package com.batch211.flashcart.services;

import com.batch211.flashcart.entities.Payment;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.enums.PaymentStatus;

public interface PaymentService {
    Payment recordPaymentResult(User user,
                           
                                String razorpayOrderId,
                                String razorpayPaymentId,
                                PaymentStatus status);
}