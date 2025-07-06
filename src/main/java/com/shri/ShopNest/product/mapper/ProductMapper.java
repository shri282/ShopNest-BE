package com.shri.ShopNest.product.mapper;

import com.shri.ShopNest.product.dto.ProductDto;
import com.shri.ShopNest.product.model.Product;

public class ProductMapper {

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .brand(product.getBrand())
                .name(product.getName())
                .category(product.getCategory())
                .availability(product.isAvailability())
                .prize(product.getPrize())
                .imageType(product.getImageType())
                .imageName(product.getImageName())
                .quantity(product.getQuantity())
                .imageURL(product.getImageURL())
                .build();
    }
}
