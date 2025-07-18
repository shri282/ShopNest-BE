package com.shri.ShopNest.product.mapper;

import com.shri.ShopNest.product.dto.CreateProductReqDto;
import com.shri.ShopNest.product.dto.ProductDto;
import com.shri.ShopNest.product.dto.UpdateProductReqDto;
import com.shri.ShopNest.product.model.Product;

public class ProductMapper {

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
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

    public static Product toProductEntity(UpdateProductReqDto dto) {
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

    public static Product toProductEntity(CreateProductReqDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setAvailability(dto.isAvailability());
        product.setPrize(dto.getPrize());
        product.setQuantity(dto.getQuantity());
        product.setCategoryName(dto.getCategoryName());

        return product;
    }

}
