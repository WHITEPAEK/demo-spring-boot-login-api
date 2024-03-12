package com.whitepaek.api.request;

import lombok.Getter;

@Getter
public class AuthRequest {

    private String loginType;
    private String code;

    public AuthRequest(String loginType, String code) {
        this.loginType = loginType;
        this.code = code;
    }
}