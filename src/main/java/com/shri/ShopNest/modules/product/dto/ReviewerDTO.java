package com.shri.ShopNest.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReviewerDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private LocalDate joinDate;
}
