package com.shri.ShopNest.cart.repo;

import com.shri.ShopNest.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
