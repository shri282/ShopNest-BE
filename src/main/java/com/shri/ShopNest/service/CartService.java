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

    public CartService(CartRepo cartRepo, UserRepo userRepo,
                       CartItemRepo cartItemRepo, CartMapper cartMapper) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.cartItemRepo = cartItemRepo;
        this.cartMapper = cartMapper;
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
            cart.calculateCartTotals();
            return cartRepo.save(cart);
        }

        CartItem cartItem = new CartItem(cart, product);
        cart.addItem(cartItem);
        return cartRepo.save(cart);
    }
    public Cart getOrCreateCart(long userId) throws ResourceNotFoundException {
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

    public void delete(long id) {
        cartRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        cartRepo.deleteById(id);
    }

    @Transactional
    public CartDto addCartItemQuantity(long itemId, int quantity) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        cartItem.addQuantity(quantity);
        Cart cart = cartItem.getCart();
        cart.calculateCartTotals();

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public CartDto removeCartItem(long itemId) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));

        Cart cart = cartItem.getCart();
        cart.removeItem(cartItem);

        return cartMapper.toCartDTO(cartRepo.save(cart));
    }

    public List<Cart> getAbandonedCarts() {
        return cartRepo.findByStatus(CartStatus.ACTIVE);
    }
}
