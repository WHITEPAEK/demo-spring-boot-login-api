package com.whitepaek.service.request;

import com.whitepaek.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthServiceRequest {

    private String loginType;
    private Long loginId;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public AuthServiceRequest(String loginType, Long loginId, String nickname, String profileImageUrl) {
        this.loginType = loginType;
        this.loginId = loginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public Member toEntity() {
        return Member.builder()
                .loginType(loginType)
                .loginId(loginId)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
