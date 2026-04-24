package com.batch211.flashcart.repo;

import com.batch211.flashcart.entities.Payment;
import com.batch211.flashcart.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderId(Order order);
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}