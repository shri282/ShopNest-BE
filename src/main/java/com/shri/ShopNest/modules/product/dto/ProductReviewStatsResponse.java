package com.shri.ShopNest.modules.product.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ProductReviewStatsResponse {
    private double averageRating;
    private long totalRatings;
    private Map<Integer, Long> counts;
    private Map<Integer, Double> percentages;
}
