package com.shri.ShopNest.service;

import com.shri.ShopNest.dto.cart.CartDto;
import com.shri.ShopNest.enums.CartStatus;
import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.mapper.CartMapper;
import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.CartItem;
import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.repo.CartItemRepo;
import com.shri.ShopNest.repo.CartRepo;
import com.shri.ShopNest.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartMapper cartMapper;

    public Optional<Cart> findUserActiveCart(long userId) {
        return cartRepo.findByUserId(userId).stream()
                .filter(cart -> cart.getStatus().isActive())
                .findFirst();
    }

    @Transactional
    public Cart addToCart(long userId, Product product) {
        Cart cart = getOrCreateCart(userId, product);
        Optional<CartItem> cartItemOp = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst();

        if (cartItemOp.isPresent()) {
            CartItem cartItem = cartItemOp.get();
            cartItem.addQuantity(1);
            cart.calculateCartTotals();
            return cartRepo.save(cart);
        }

        CartItem cartItem = new CartItem(cart, product);
        cart.addItem(cartItem);
        return cartRepo.save(cart);
    }
    public Cart getOrCreateCart(long userId, Product product) {
        List<Cart> carts = cartRepo.findByUserId(userId);
        User user = userRepo.findById((int) userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (!carts.isEmpty()) {
            Optional<Cart> cart = carts.stream()
                    .filter(ele -> ele.getStatus().isActive())
                    .findFirst();

            if (cart.isPresent()) {
                return cart.get();
            }
        }

        Cart cart = new Cart(user, "INR", LocalDateTime.now().plusWeeks(2));
        return cartRepo.save(cart);
    }

    public void delete(long id) throws Exception {
        cartRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        cartRepo.deleteById(id);
    }

    @Transactional
    public CartDto addCartItemQuantity(long itemId, int quantity) throws Exception {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        cartItem.addQuantity(quantity);
        Cart cart = cartItem.getCart();
        cart.calculateCartTotals();

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public CartDto removeCartItem(long itemId) throws Exception {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        Cart cart = cartItem.getCart();
        cart.removeItem(cartItem);

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public List<Cart> getAbandonedCarts() {
        return cartRepo.findByStatus(CartStatus.ACTIVE);
    }
}
