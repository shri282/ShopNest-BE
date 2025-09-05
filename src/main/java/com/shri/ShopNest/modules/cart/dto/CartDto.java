package com.shri.ShopNest.modules.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {
    private Long id;
    private Long userId;
    private String status;
    private double subtotalPrice;
    private double discountAmount;
    private double taxAmount;
    private double shippingCost;
    private double grandTotal;
    private String currency;
    private List<CartItemDto> items;
}
