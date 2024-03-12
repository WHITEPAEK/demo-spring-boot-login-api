package com.whitepaek.service;

import com.whitepaek.entity.Member;
import com.whitepaek.repository.MemberRepository;
import com.whitepaek.service.request.AuthServiceRequest;
import com.whitepaek.service.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthResponse authorizationProcess(AuthServiceRequest authServiceRequest) {
        Optional<Member> optionalMember = memberRepository.findByLoginTypeAndLoginId(authServiceRequest.getLoginType(), authServiceRequest.getLoginId());
        Member member;

        if (optionalMember.isPresent()) {
            // 기존 멤버
            member = optionalMember.get();
            member.login(authServiceRequest.getNickname(), authServiceRequest.getProfileImageUrl());
        } else {
            // 신규 멤버
            member = authServiceRequest.toEntity();
            memberRepository.save(member);
        }

        return AuthResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
