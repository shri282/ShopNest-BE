package com.shri.ShopNest.modules.wishlist.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WishlistSummaryDto {
    private Long id;
    private String name;
    private boolean isDefault;
}
