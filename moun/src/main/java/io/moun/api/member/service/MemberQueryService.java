package io.moun.api.member.service;

import io.moun.api.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MemberQueryService {
    List<Member> findAllWithPositions();

    Member findWithPositionsById(Long id);

    Member findById(Long id);
    //    public MemberResponse findByUsername(String username);

}
