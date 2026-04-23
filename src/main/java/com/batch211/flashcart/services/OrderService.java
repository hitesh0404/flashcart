package com.batch211.flashcart.services;

import com.batch211.flashcart.dto.OrderCreateResponceDto;
import com.batch211.flashcart.entities.Order;
import com.batch211.flashcart.entities.User;

import java.util.List;

public interface OrderService {
    OrderCreateResponceDto placeOrder(User user, Long addressId);
    List<Order> getUserOrders(User user);
    Order getUserOrderById(User user, Integer orderId);
}