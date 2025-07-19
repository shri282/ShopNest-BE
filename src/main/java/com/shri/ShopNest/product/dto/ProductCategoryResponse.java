package com.shri.ShopNest.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private Integer level;
    private Boolean isLeaf;
    private Boolean isActive;
    private Integer displayOrder;
    private String imageUrl;
    private String iconUrl;
}
