package com.shri.ShopNest.modules.product.dto;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductReq {

    @Positive(message = "Product ID must be a positive number")
    private int id;

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String name;

    private String description;

    @NotBlank(message = "Brand must not be blank")
    @Size(max = 100, message = "Brand name must not exceed 100 characters")
    private String brand;

    @NotNull(message = "Category ID must not be null")
    @Positive(message = "Category ID must be a positive number")
    private Long categoryId;

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String categoryName;

    @DecimalMin(value = "0.0", inclusive = false, message = "Prize must be greater than 0")
    private double prize;

    @PositiveOrZero(message = "Quantity must be zero or positive")
    private int quantity;

    private boolean availability;

    private String imageURL;
}
