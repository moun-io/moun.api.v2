package io.moun.api.member.service;

import io.moun.api.member.domain.Member;

import java.security.Principal;

public interface MemberService {
    public Member findById(Long id);
    public void save(Member member);
    public void saveDefault(Principal principal);

}
