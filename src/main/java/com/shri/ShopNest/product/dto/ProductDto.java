package com.shri.ShopNest.product.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private double prize;
    private boolean availability;
    private int quantity;
    private String category;
    private String brand;
    private String imageName;
    private String imageType;
    private String imageURL;
}
