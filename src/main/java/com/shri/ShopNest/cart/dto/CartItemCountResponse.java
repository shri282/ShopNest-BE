package com.shri.ShopNest.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemCountResponse {
    private Integer totalItems;
}