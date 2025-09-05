package com.shri.ShopNest.modules.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemDto {
    private Long id;
    private Long wishlistId;
    private int productId;
    private String productName;
    private Integer priority;
    private String notes;
    private LocalDateTime addedAt;
}
