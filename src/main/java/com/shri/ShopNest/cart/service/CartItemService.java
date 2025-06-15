package com.shri.ShopNest.cart.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.cart.model.CartItem;
import com.shri.ShopNest.cart.repo.CartItemRepo;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemRepo cartItemRepo;

    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    public CartItem findOne(long itemId) throws ResourceNotFoundException {
        return cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
    }

    public void delete(long id) throws ResourceNotFoundException {
        cartItemRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
        cartItemRepo.deleteById(id);
    }

}
