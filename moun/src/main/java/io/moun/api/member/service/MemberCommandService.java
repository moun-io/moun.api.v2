package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Transactional
public interface MemberCommandService {

    Member save(Member member);

    Member saveDefault();

    Member update(Member member, Long id);
}
