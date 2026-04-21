package com.batch211.flashcart.repo;

import com.batch211.flashcart.entities.Cart;
import com.batch211.flashcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);
}