package com.shri.ShopNest.modules.user.mapper;

import com.shri.ShopNest.modules.user.dto.AddressResponse;
import com.shri.ShopNest.modules.user.dto.CreateAddressReq;
import com.shri.ShopNest.modules.user.dto.UpdateAddressReq;
import com.shri.ShopNest.modules.user.model.UserAddress;

public class AddressMapper {
    public static UserAddress toEntity(CreateAddressReq dto) {
        return UserAddress.builder()
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .isDefault(dto.getIsDefault() != null ? dto.getIsDefault() : false)
                .addressType(dto.getAddressType() != null ? dto.getAddressType() : null)
                .build();
    }

    public static UserAddress toEntity(UpdateAddressReq dto) {
        return UserAddress.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .isDefault(dto.getIsDefault())
                .addressType(dto.getAddressType())
                .build();
    }

    public static AddressResponse toDto(UserAddress address) {
        return AddressResponse.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phoneNumber(address.getPhoneNumber())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .isDefault(address.getIsDefault())
                .addressType(address.getAddressType())
                .build();
    }
}
