package com.shri.ShopNest.pojo;

import com.shri.ShopNest.model.User;

public class SuccessfulLoginResponse {

    private User user;

    private String token;

    public SuccessfulLoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
