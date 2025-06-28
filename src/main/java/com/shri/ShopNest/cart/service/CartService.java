package com.shri.ShopNest.cart.service;

import com.shri.ShopNest.cart.dto.CartDto;
import com.shri.ShopNest.cart.enums.CartStatus;
import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.cart.mapper.CartMapper;
import com.shri.ShopNest.cart.model.Cart;
import com.shri.ShopNest.cart.model.CartItem;
import com.shri.ShopNest.product.model.Product;
import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.cart.repo.CartItemRepo;
import com.shri.ShopNest.cart.repo.CartRepo;
import com.shri.ShopNest.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;
    private final CartCalculator cartCalculator;

    public CartService(CartRepo cartRepo, UserRepo userRepo,
                       CartItemRepo cartItemRepo, CartMapper cartMapper,
                       CartCalculator cartCalculator) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.cartItemRepo = cartItemRepo;
        this.cartMapper = cartMapper;
        this.cartCalculator = cartCalculator;
    }


    public Optional<Cart> findUserActiveCart(long userId) {
        return cartRepo.findByUserId(userId).stream()
                .filter(cart -> cart.getStatus().isActive())
                .findFirst();
    }

    @Transactional
    public Cart addToCart(long userId, Product product) {
        Cart cart = getOrCreateCart(userId);
        Optional<CartItem> cartItemOp = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst();

        if (cartItemOp.isPresent()) {
            CartItem cartItem = cartItemOp.get();
            cartItem.addQuantity(1);
            cartCalculator.calculateTotals(cart);
            return cartRepo.save(cart);
        }

        CartItem cartItem = new CartItem(cart, product);
        cart.getItems().add(cartItem);
        cartCalculator.calculateTotals(cart);
        return cartRepo.save(cart);
    }
    public Cart getOrCreateCart(long userId) throws ResourceNotFoundException {
        List<Cart> carts = cartRepo.findByUserId(userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

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

    public void delete(long id) {
        cartRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        cartRepo.deleteById(id);
    }

    @Transactional
    public CartDto addCartItemQuantity(long itemId, int quantity) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        cartItem.addQuantity(quantity);
        Cart cart = cartItem.getCart();
        cartCalculator.calculateTotals(cart);

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public CartDto removeCartItem(long itemId) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        Cart cart = cartItem.getCart();
        cart.getItems().removeIf(item -> item.getId().equals(cartItem.getId()));
        cartCalculator.calculateTotals(cart);

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public List<Cart> getAbandonedCarts() {
        return cartRepo.findByStatus(CartStatus.ACTIVE);
    }
}
