package com.shri.ShopNest.cart.repo;

import com.shri.ShopNest.cart.enums.CartStatus;
import com.shri.ShopNest.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(long userId);

    List<Cart> findByStatus(CartStatus status);

}
