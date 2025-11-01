package com.shri.ShopNest.modules.cart.service;

import com.shri.ShopNest.model.Cart;
import com.shri.ShopNest.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartCalculator {
    public void calculateTotals(Cart cart) {
        int totalQuantity = 0;
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            totalQuantity += item.getQuantity();
            subtotal = subtotal.add(item.getTotalPrice());
        }

        cart.setTotalQuantity(totalQuantity);
        cart.setSubtotalPrice(subtotal);
        cart.setTotalItems(cart.getItems().size());
        cart.setGrandTotal(subtotal);
    }
}
