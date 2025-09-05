package com.shri.ShopNest.modules.cart.repo;

import com.shri.ShopNest.modules.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
