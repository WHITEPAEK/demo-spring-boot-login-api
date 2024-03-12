package com.whitepaek.repository;

import com.whitepaek.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginTypeAndLoginId(String loginType, Long loginId);

}
