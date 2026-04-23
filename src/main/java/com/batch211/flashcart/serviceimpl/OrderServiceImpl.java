package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.dto.OrderCreateResponceDto;
import com.batch211.flashcart.entities.*;
import com.batch211.flashcart.enums.PaymentStatus;
import com.batch211.flashcart.repo.AddressRepository;
import com.batch211.flashcart.repo.CartRepository;
import com.batch211.flashcart.repo.OrderItemRepository;
import com.batch211.flashcart.repo.OrderRepository;
import com.batch211.flashcart.repo.PaymentRepository;
import com.batch211.flashcart.services.OrderService;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	
	private static final String KEY_ID = "rzp_test_SeYjHljAhVr7Lm"; // Replace with your Key ID
    private static final String KEY_SECRET = "qpaH6OKim61MYjdjvGoq1sMZ"; // Replace with your Key Secret

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository payRepo;
    
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartRepository cartRepository,
                            AddressRepository addressRepository,
                            PaymentRepository payRepo) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.payRepo =  payRepo;
    }

    @Override
    public OrderCreateResponceDto placeOrder(User user, Long addressId) {
    	
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
        
        try {
			RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
			JSONObject orderRequest = new JSONObject();
	        
	        orderRequest.put("amount", amount * 100);
	        orderRequest.put("currency", "INR");
	        com.razorpay.Order rzorder = razorpay.orders.create(orderRequest);
	        order.setRazorpayOrderId(rzorder.get("id"));
	        payRepo.save(new Payment(null, savedOrder, user, amount, 
	        		rzorder.get("id"), null, null, PaymentStatus.PENDING));
	        return new OrderCreateResponceDto(rzorder,savedOrder);
        } catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        // clear cart
        cartRepository.deleteAll(cartItems);

        return new OrderCreateResponceDto(null,savedOrder);
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