package com.shri.ShopNest.modules.user.mapper;

import com.shri.ShopNest.model.User;
import com.shri.ShopNest.modules.user.dto.UserDto;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .dob(user.getDob())
                .gender(user.getGender())
                .roles(user.getRoles())
                .pic(user.getPic())
                .enabled(user.isEnabled())
                .accountNonLocked(user.isAccountNonLocked())
                .phNo(user.getPhNo())
                .build();
    }
}