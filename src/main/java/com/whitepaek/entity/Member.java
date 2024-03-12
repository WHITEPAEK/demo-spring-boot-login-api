package com.whitepaek.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginType;
    private Long loginId;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime lastLoginAt;

    @Builder
    private Member(Long id, String loginType, Long loginId, String nickname, String profileImageUrl) {
        this.id = id;
        this.loginType = loginType;
        this.loginId = loginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginAt = LocalDateTime.now();
    }

    public void login(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginAt = LocalDateTime.now();
    }
}
