package com.shri.ShopNest.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponse {
    private String message;
    private int status;
    private String sessionUrl;
    private String sessionId;
}
