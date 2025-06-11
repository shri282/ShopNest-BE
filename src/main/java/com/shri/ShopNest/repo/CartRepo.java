package com.shri.ShopNest.repo;

import com.shri.ShopNest.enums.CartStatus;
import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    public List<Cart> findByUserId(long userId);

    public List<Cart> findByStatus(CartStatus status);

}
