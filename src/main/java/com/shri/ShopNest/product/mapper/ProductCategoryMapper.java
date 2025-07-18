package com.shri.ShopNest.product.mapper;

import com.shri.ShopNest.product.dto.ProductCategoryDto;
import com.shri.ShopNest.product.model.ProductCategory;

public class ProductCategoryMapper {
    public static ProductCategoryDto toDto(ProductCategory category) {
        if (category == null) return null;

        return ProductCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .level(category.getLevel())
                .isLeaf(category.getIsLeaf())
                .isActive(category.getIsActive())
                .displayOrder(category.getDisplayOrder())
                .imageUrl(category.getImageUrl())
                .iconUrl(category.getIconUrl())
                .build();
    }

    public static ProductCategory toEntity(ProductCategoryDto dto) {
        if (dto == null) return null;

        ProductCategory category = new ProductCategory();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setLevel(dto.getLevel());
        category.setIsLeaf(dto.getIsLeaf());
        category.setIsActive(dto.getIsActive());
        category.setDisplayOrder(dto.getDisplayOrder());
        category.setImageUrl(dto.getImageUrl());
        category.setIconUrl(dto.getIconUrl());

        return category;
    }
}
