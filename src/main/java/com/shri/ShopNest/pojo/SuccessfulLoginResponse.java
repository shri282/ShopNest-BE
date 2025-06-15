package com.shri.ShopNest.pojo;

import com.shri.ShopNest.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessfulLoginResponse {
    private User user;
    private String token;
}
