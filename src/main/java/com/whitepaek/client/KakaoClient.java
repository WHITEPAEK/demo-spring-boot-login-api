package com.whitepaek.client;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Component
public class KakaoClient {

    private static final String AUTHORIZATION_GRANT_TYPE = "authorization_code";
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private KakaoClient(
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.client-secret}") String clientSecret,
            @Value("${kakao.redirect-uri}") String redirectUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * 카카오 토큰 발급 API
     * Ref. https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
     * @param code
     * @return
     */
    public ReadContext getToken(String code) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", AUTHORIZATION_GRANT_TYPE);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);
        var responseBody = RestClient.builder().build()
                .post()
                .uri(TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(Map.class);
        return JsonPath.parse(responseBody);
    }

    /**
     * 카카오 로그인 사용자 정보 조회 API
     * Ref. https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     * @param accessToken
     * @return
     */
    public ReadContext getUserInfo(String accessToken) {
        var responseBody = RestClient.builder().build()
                .get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(Map.class);
        return JsonPath.parse(responseBody);
    }
}
