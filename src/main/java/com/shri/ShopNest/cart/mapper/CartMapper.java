package com.shri.ShopNest.cart.mapper;

import com.shri.ShopNest.cart.dto.CartDto;
import com.shri.ShopNest.cart.dto.CartItemDto;
import com.shri.ShopNest.cart.model.Cart;
import com.shri.ShopNest.cart.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartItemDto toCartItemDTO(CartItem item) {
        return CartItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .imageURL(item.getProduct().getImageURL())
                .availability(item.getProduct().isAvailability())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice().doubleValue())
                .build();
    }

    public static CartDto toCartDTO(Cart cart) {
        List<CartItemDto> itemDTOs = cart.getItems().stream()
                .map(CartMapper::toCartItemDTO)
                .collect(Collectors.toList());

        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .status(cart.getStatus().toString())
                .subtotalPrice(cart.getSubtotalPrice().doubleValue())
                .discountAmount(cart.getDiscountAmount().doubleValue())
                .taxAmount(cart.getTaxAmount().doubleValue())
                .shippingCost(cart.getShippingCost().doubleValue())
                .grandTotal(cart.getGrandTotal().doubleValue())
                .currency(cart.getCurrency())
                .items(itemDTOs)
                .build();
    }
}