package io.moun.api.member.service;

import io.moun.api.member.domain.Member;

public interface MemberQueryService {
    public Member findWithPositionsById(Long id);
    public Member findById(Long id);
    //    public MemberResponse findByUsername(String username);

}
