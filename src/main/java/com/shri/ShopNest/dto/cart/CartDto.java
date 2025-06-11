package com.shri.ShopNest.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
