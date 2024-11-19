package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.domain.Member;

import java.security.Principal;

public interface MemberCommandService {

    Member save(Member member);

    Member saveDefault();

    Member update(Member member, Long id);
}
