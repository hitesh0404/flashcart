package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.entities.*;
import com.batch211.flashcart.repo.AddressRepository;
import com.batch211.flashcart.repo.CartRepository;
import com.batch211.flashcart.repo.OrderItemRepository;
import com.batch211.flashcart.repo.OrderRepository;
import com.batch211.flashcart.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartRepository cartRepository,
                            AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Order placeOrder(User user, Long addressId) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // calculate total amount
        int amount = cartItems.stream()
                .mapToInt(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setAmount(amount);
        order.setAddress(address);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(savedOrder);
            oi.setProduct(cartItem.getProduct());
            orderItems.add(oi);
        }
        orderItemRepository.saveAll(orderItems);

        savedOrder.setOrderItems(orderItems);

        // clear cart
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByOrderIdDesc(user);
    }

    @Override
    public Order getUserOrderById(User user, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized order access");
        }
        return order;
    }
}