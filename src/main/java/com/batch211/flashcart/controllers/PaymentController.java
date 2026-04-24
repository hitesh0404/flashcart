package com.batch211.flashcart.controllers;

import com.batch211.flashcart.entities.Payment;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.enums.PaymentStatus;
import com.batch211.flashcart.services.PaymentService;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

	@Autowired
    private  PaymentService paymentService;
	
	private static final String KEY_SECRET = "qpaH6OKim61MYjdjvGoq1sMZ"; // Replace with your Key Secret

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Frontend calls this after successful Razorpay payment
    @PostMapping("/complete")
    public ResponseEntity<Payment> completePayment(
            @AuthenticationPrincipal User user,
            @RequestParam("razorpay_order_id") String razorpayOrderId,
            @RequestParam("razorpay_payment_id") String razorpayPaymentId,
            @RequestParam("razorpay_signature") String razorpaySignature,
            @RequestParam PaymentStatus status
    )throws RazorpayException {
    	
    	
        Payment payment = paymentService.recordPaymentResult(
                user, razorpayOrderId, razorpayPaymentId, status);
        
        try {
            // Verify the payment signature here
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, KEY_SECRET);

            if (isValid) {
            	payment.setPaymentStatus(PaymentStatus.SUCCESS);
            	return ResponseEntity.ok(payment);
            } else {
            	payment.setPaymentStatus(PaymentStatus.FAILED);
            	return ResponseEntity.ok(payment);
            }
        } catch (RazorpayException e) {
            System.err.println("Razorpay Exception during callback: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General Exception during callback: " + e.getMessage());
            throw new RazorpayException("General exception during callback");
        }
        
//        return ResponseEntity.ok(payment);
    }
}