package com.batch211.flashcart.services;

import com.batch211.flashcart.entities.Cart;
import com.batch211.flashcart.entities.User;

import java.util.List;

public interface CartService {
    List<Cart> getUserCart(User user);
    Cart addToCart(User user, Integer productId, int quantity);
    Cart updateCartItem(User user, Integer cartId, int quantity);
    void removeCartItem(User user, Integer cartId);
    void clearCart(User user);
}