package com.batch211.flashcart.controllers;

import com.batch211.flashcart.entities.Cart;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getUserCart(user));
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Integer productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        Cart added = cartService.addToCart(user, productId, quantity);
        return ResponseEntity.ok(added);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Integer cartId,
            @RequestParam int quantity
    ) {
        Cart updated = cartService.updateCartItem(user, cartId, quantity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Integer cartId
    ) {
        cartService.removeCartItem(user, cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}