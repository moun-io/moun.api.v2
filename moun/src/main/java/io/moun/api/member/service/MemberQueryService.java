package io.moun.api.member.service;

import io.moun.api.member.domain.Member;

public interface MemberQueryService {
    Member findWithPositionsById(Long id);

    Member findById(Long id);
    //    public MemberResponse findByUsername(String username);

}
