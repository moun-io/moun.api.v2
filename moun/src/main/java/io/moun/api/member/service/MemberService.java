package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.domain.Member;

import java.security.Principal;

public interface MemberService {
    public Member findById(Long id);

    public MemberResponse findMemberResponseById(Long id);

    public Member save(Member member);

    public Member saveDefault();
//    public MemberResponse findByUsername(String username);
}
