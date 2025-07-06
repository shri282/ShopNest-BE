package com.shri.ShopNest.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private int productId;
    private String productName;
    private String imageURL;
    private boolean availability;
    private int quantity;
    private double unitPrice;
}
