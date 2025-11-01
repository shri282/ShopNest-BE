package com.shri.ShopNest.modules.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuth {
    public String buildLoginUrl(String state);
    public String callback(String code) throws JsonProcessingException;
}
