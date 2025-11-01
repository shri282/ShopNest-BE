package com.shri.ShopNest.modules.product.mapper;

import com.shri.ShopNest.modules.product.dto.CreateProductReq;
import com.shri.ShopNest.modules.product.dto.ProductResponse;
import com.shri.ShopNest.modules.product.dto.UpdateProductReq;
import com.shri.ShopNest.model.Product;

public class ProductMapper {

    public static ProductResponse toProductDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .brand(product.getBrand())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategoryName())
                .availability(product.isAvailability())
                .prize(product.getPrize())
                .imageType(product.getImageType())
                .imageName(product.getImageName())
                .quantity(product.getQuantity())
                .imageURL(product.getImageURL())
                .build();
    }

    public static Product toProductEntity(UpdateProductReq dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setAvailability(dto.isAvailability());
        product.setPrize(dto.getPrize());
        product.setQuantity(dto.getQuantity());
        product.setCategoryName(dto.getCategoryName());

        return product;
    }

    public static Product toProductEntity(CreateProductReq dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setAvailability(dto.getAvailability());
        product.setPrize(dto.getPrize());
        product.setQuantity(dto.getQuantity());
        product.setCategoryName(dto.getCategoryName());

        return product;
    }

}
