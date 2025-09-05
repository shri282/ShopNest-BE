package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.modules.user.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAddressReq {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Address Line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required")
    private String city;

    private String state;

    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    private Boolean isDefault = false;

    private AddressType addressType = AddressType.HOME;
}
