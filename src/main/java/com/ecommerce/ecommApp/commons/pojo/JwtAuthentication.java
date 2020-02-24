package com.ecommerce.ecommApp.commons.pojo;

import lombok.Data;

@Data
public class JwtAuthentication {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthentication(String accessToken) {
        this.accessToken = accessToken;
    }

}