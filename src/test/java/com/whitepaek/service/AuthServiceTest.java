package com.whitepaek.service;

import com.whitepaek.entity.Member;
import com.whitepaek.repository.MemberRepository;
import com.whitepaek.service.request.AuthServiceRequest;
import com.whitepaek.service.response.AuthResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private AuthService authService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("기존 멤버 로그인 요청 처리")
    void givenExistedMember_whenAuthorizationProcess_thenUpdatedMember() throws Exception {
        // given
        Member existedMember = Member.builder()
                .loginType("kakao")
                .loginId(1L)
                .nickname("SEUNGJOO PAEK")
                .profileImageUrl("https://avatars.githubusercontent.com/u/41967243?v=4")
                .build();
        memberRepository.save(existedMember);

        AuthServiceRequest authServiceRequest = AuthServiceRequest.builder()
                .loginType(existedMember.getLoginType())
                .loginId(existedMember.getLoginId())
                .nickname("whitepaek")
                .profileImageUrl("https://avatars.githubusercontent.com/u/41967243?v=4")
                .build();

        // when
        AuthResponse authResponse = authService.authorizationProcess(authServiceRequest);

        // then
        assertThat(authResponse.getId()).isEqualTo(existedMember.getId());
        assertThat(authResponse.getNickname()).isEqualTo(authServiceRequest.getNickname());
        assertThat(authResponse.getProfileImageUrl()).isEqualTo(authServiceRequest.getProfileImageUrl());
    }

    @Test
    @DisplayName("신규 멤버 로그인 요청 처리")
    void givenNewMember_whenAuthorizationProcess_thenCreatedMember() throws Exception {
        // given
        AuthServiceRequest authServiceRequest = AuthServiceRequest.builder()
                .loginType("kakao")
                .loginId(1L)
                .nickname("SEUNGJOO PAEK")
                .profileImageUrl("https://avatars.githubusercontent.com/u/41967243?v=4")
                .build();

        // when
        AuthResponse authResponse = authService.authorizationProcess(authServiceRequest);

        // then
        assertThat(authResponse.getId()).isEqualTo(1L);
        assertThat(authResponse.getNickname()).isEqualTo(authServiceRequest.getNickname());
        assertThat(authResponse.getProfileImageUrl()).isEqualTo(authServiceRequest.getProfileImageUrl());
    }

}