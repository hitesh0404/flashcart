package com.batch211.flashcart.repo;

import com.batch211.flashcart.entities.Order;
import com.batch211.flashcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserOrderByOrderIdDesc(User user);
    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
}