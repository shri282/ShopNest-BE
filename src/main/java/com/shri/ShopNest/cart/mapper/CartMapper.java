package com.shri.ShopNest.cart.mapper;

import com.shri.ShopNest.cart.dto.CartDto;
import com.shri.ShopNest.cart.dto.CartItemDto;
import com.shri.ShopNest.cart.model.Cart;
import com.shri.ShopNest.cart.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartItemDto toCartItemDTO(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setImageURL(item.getProduct().getImageURL());
        dto.setAvailability(item.getProduct().isAvailability());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice().doubleValue());
        return dto;
    }

    public CartDto toCartDTO(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setStatus(cart.getStatus().toString());
        dto.setSubtotalPrice(cart.getSubtotalPrice().doubleValue());
        dto.setDiscountAmount(cart.getDiscountAmount().doubleValue());
        dto.setTaxAmount(cart.getTaxAmount().doubleValue());
        dto.setShippingCost(cart.getShippingCost().doubleValue());
        dto.setGrandTotal(cart.getGrandTotal().doubleValue());
        dto.setCurrency(cart.getCurrency());

        List<CartItemDto> itemDTOs = cart.getItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
}