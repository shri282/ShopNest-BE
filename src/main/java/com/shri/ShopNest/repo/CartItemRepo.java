package com.shri.ShopNest.repo;

import com.shri.ShopNest.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
