package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.entities.Cart;
import com.batch211.flashcart.entities.Product;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.repo.CartRepository;
import com.batch211.flashcart.repo.ProductRepository;
import com.batch211.flashcart.services.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Cart> getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart addToCart(User user, Integer productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // check if already in cart
        List<Cart> cartItems = cartRepository.findByUser(user);
        Cart existing = cartItems.stream()
                .filter(ci -> ci.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepository.save(existing);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartItem(User user, Integer cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized cart access");
        }
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(User user, Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized cart access");
        }
        cartRepository.delete(cart);
    }

    @Override
    public void clearCart(User user) {
        List<Cart> carts = cartRepository.findByUser(user);
        cartRepository.deleteAll(carts);
    }
}