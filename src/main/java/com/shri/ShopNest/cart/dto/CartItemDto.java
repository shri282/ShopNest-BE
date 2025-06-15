package com.shri.ShopNest.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private int productId;
    private String productName;
    private byte[] image;
    private boolean availability;
    private int quantity;
    private double unitPrice;
}
