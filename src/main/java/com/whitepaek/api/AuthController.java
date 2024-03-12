package com.whitepaek.api;

import com.jayway.jsonpath.ReadContext;
import com.whitepaek.api.request.AuthRequest;
import com.whitepaek.client.KakaoClient;
import com.whitepaek.service.AuthService;
import com.whitepaek.service.request.AuthServiceRequest;
import com.whitepaek.service.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final KakaoClient kakaoClient;
    private final AuthService authService;

    @PostMapping("/auth/authorize")
    public ResponseEntity<AuthResponse> authorize(@RequestBody AuthRequest authRequest) {

        ReadContext tokenAttributes = kakaoClient.getToken(authRequest.getCode()); // 카카오 인증 서버에 Token 발급 요청
        String accessToken = tokenAttributes.read("$.access_token");

        ReadContext userAttributes = kakaoClient.getUserInfo(accessToken); // 카카오 인증 서버에 로그인 사용자 정보 요청
        Long loginId = userAttributes.read("$.id");
        String nickname = userAttributes.read("$.kakao_account.profile.nickname");
        String profileImageUrl = userAttributes.read("$.kakao_account.profile.profile_image_url");

        AuthServiceRequest authServiceRequest = AuthServiceRequest.builder()
                .loginId(loginId)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
        AuthResponse authResponse = authService.authorizationProcess(authServiceRequest); // 로그인 사용자 인증 처리
        return ResponseEntity.ok(authResponse);
    }

}
