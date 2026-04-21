package com.batch211.flashcart.controllers;

import com.batch211.flashcart.entities.Order;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place order using current cart
    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @AuthenticationPrincipal User user,
            @RequestParam Long addressId
    ) {
        Order order = orderService.placeOrder(user, addressId);
        return ResponseEntity.ok(order);
    }

    // Order history
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Integer orderId
    ) {
        return ResponseEntity.ok(orderService.getUserOrderById(user, orderId));
    }
}