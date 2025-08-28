package com.shri.ShopNest.product.dto;

import com.shri.ShopNest.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReviewResponse {
    private Long id;
    private User reviewer;
    private float rating;
    private String title;
    private String content;
    private String mediaUrls;
    private boolean verifiedPurchase;
    private int helpfulCount;
    private int reportCount;
}
