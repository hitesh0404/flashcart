package com.batch211.flashcart.controllers;

import com.batch211.flashcart.entities.Payment;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.enums.PaymentStatus;
import com.batch211.flashcart.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

	@Autowired
    private  PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Frontend calls this after successful Razorpay payment
    @PostMapping("/complete")
    public ResponseEntity<Payment> completePayment(
            @AuthenticationPrincipal User user,
            @RequestParam Integer orderId,
            @RequestParam String razorpayOrderId,
            @RequestParam String razorpayPaymentId,
            @RequestParam PaymentStatus status
    ) {
        Payment payment = paymentService.recordPaymentResult(
                user, orderId, razorpayOrderId, razorpayPaymentId, status);
        return ResponseEntity.ok(payment);
    }
}