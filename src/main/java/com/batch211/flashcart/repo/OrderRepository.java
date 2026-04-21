package com.batch211.flashcart.repo;

import com.batch211.flashcart.entities.Order;
import com.batch211.flashcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserOrderByOrderIdDesc(User user);
}