package com.shri.ShopNest.product.mapper;

import com.shri.ShopNest.product.dto.*;
import com.shri.ShopNest.product.enums.ReviewStatus;
import com.shri.ShopNest.product.model.ProductReview;

public class ProductReviewMapper {
    public static ProductReviewResponse toProductReviewDto(ProductReview productReview) {
        ReviewerDTO reviewerDTO = ReviewerDTO.builder()
                .username(productReview.getReviewer().getUsername())
                .avatarUrl("")
                .id(productReview.getReviewer().getId())
                .joinDate(productReview.getReviewer().getCreatedAt().toLocalDate()).build();

        return ProductReviewResponse.builder()
                .id(productReview.getId())
                .title(productReview.getTitle())
                .content(productReview.getContent())
                .mediaUrls(productReview.getMediaUrls())
                .rating(productReview.getRating())
                .reviewer(reviewerDTO)
                .verifiedPurchase(productReview.isVerifiedPurchase())
                .reportCount(productReview.getReportCount())
                .helpfulCount(productReview.getHelpfulCount())
                .createdAt(productReview.getCreatedAt())
                .build();
    }

    public static ProductReview toProductReviewEntity(CreateProductReviewReq createProductReviewReq) {
        return ProductReview.builder()
                .rating(createProductReviewReq.getRating())
                .status(ReviewStatus.ACTIVE)
                .content(createProductReviewReq.getContent())
                .title(createProductReviewReq.getTitle())
                .verifiedPurchase(true)
                .build();
    }
}
