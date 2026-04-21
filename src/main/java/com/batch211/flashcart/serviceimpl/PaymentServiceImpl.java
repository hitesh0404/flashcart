package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.entities.*;
import com.batch211.flashcart.enums.PaymentStatus;
import com.batch211.flashcart.repo.OrderRepository;
import com.batch211.flashcart.repo.PaymentRepository;
import com.batch211.flashcart.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
	@Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
	private  OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment recordPaymentResult(User user,
                                       Integer orderId,
                                       String razorpayOrderId,
                                       String razorpayPaymentId,
                                       PaymentStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized order access");
        }

        // update order
        order.setRazorpayOrderId(razorpayOrderId);
        order.setPaymentId(razorpayPaymentId);
        order.setStatus(status == PaymentStatus.SUCCESS ? "COMPLETED" : "FAILED");

        Payment payment = new Payment();
        payment.setOrderId(order);
        payment.setUser(user);
        payment.setAmount(order.getAmount());
        payment.setRazorpayOrderId(razorpayOrderId);
        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setPaymentStatus(status);

        return paymentRepository.save(payment);
    }
}