package io.moun.api.member.service;

import io.moun.api.member.domain.Member;

import java.util.List;

public interface MemberQueryService {
    List<Member> findAllWithPositions();

    Member findWithPositionsById(Long id);

    Member findById(Long id);
    //    public MemberResponse findByUsername(String username);

}
