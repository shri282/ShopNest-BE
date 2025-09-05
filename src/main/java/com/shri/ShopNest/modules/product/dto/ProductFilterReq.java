package com.shri.ShopNest.modules.product.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ProductFilterReq {

    private Boolean availability;

    @Size(max = 255)
    private String brand;

    @Size(max = 255)
    private String categoryName;

    @Size(max = 255)
    private String name;

    @DecimalMin("0.0")
    private Double minPrize;

    @DecimalMin("0.0")
    private Double maxPrize;

    @Min(0)
    private Integer minQuantity;

    @Min(0)
    private Integer maxQuantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdBefore;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedBefore;

    private Long categoryId;

}
