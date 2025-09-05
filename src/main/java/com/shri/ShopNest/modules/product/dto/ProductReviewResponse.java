package com.shri.ShopNest.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductReviewResponse {
    private Long id;
    private ReviewerDTO reviewer;
    private float rating;
    private String title;
    private String content;
    private String mediaUrls;
    private boolean verifiedPurchase;
    private int helpfulCount;
    private int reportCount;
    private LocalDateTime createdAt;
}
