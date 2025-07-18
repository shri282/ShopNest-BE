package com.shri.ShopNest.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateRequestDto {
    private int id;
    private String name;
    private String description;
    private String brand;
    private Long categoryId;
    private String categoryName;
    private boolean availability;
    private double prize;
    private int quantity;
    private String imageURL;

}

