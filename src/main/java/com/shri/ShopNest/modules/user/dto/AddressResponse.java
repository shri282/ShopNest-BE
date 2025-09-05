package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.modules.user.enums.AddressType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Boolean isDefault;
    private AddressType addressType;
}