package com.shri.ShopNest.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private double prize;
    private boolean availability;
    private int quantity;
    private Long categoryId;
    private String categoryName;
    private String brand;
    private String imageName;
    private String imageType;
    private String imageURL;
}
