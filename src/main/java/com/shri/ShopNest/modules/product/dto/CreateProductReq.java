package com.shri.ShopNest.modules.product.dto;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductReq {

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Brand is required")
    @Size(max = 255, message = "Brand must not exceed 255 characters")
    private String brand;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    @Size(max = 255, message = "Category name must not exceed 255 characters")
    private String categoryName;

    @NotNull(message = "Availability is required")
    private Boolean availability;

    @DecimalMin(value = "0.0", inclusive = false, message = "Prize must be greater than 0")
    private double prize;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}