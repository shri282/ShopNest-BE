package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.enums.Gender;
import com.shri.ShopNest.enums.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDate dob;
    private Gender gender;
    private List<UserRole> roles;
    private boolean enabled;
    private boolean accountNonLocked;
    private String phNo;
}
