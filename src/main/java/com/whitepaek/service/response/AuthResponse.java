package com.whitepaek.service.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public AuthResponse(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
