package com.shri.ShopNest.product.dto;

import lombok.Data;

@Data
public class CreateProductReviewReq {
    private float rating;
    private String title;
    private String content;
}
