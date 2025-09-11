package com.shri.ShopNest.modules.wishlist.dto;

import lombok.Data;

@Data
public class AddWishlistItemRequest {
    private Long wishlistId;
    private int productId;
    private String notes;
    private Integer priority;
}
