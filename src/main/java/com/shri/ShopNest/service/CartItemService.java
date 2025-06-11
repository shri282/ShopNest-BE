package com.shri.ShopNest.service;

import com.shri.ShopNest.dto.cart.CartItemDto;
import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.CartItem;
import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.repo.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    public CartItem findOne(long itemId) throws Exception {
        return cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
    }

    public void delete(long id) throws Exception {
        cartItemRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
        cartItemRepo.deleteById(id);
    }

}
