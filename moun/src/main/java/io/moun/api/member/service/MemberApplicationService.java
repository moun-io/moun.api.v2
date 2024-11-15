package io.moun.api.member.service;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;

public interface MemberApplicationService {
    public Member registerMemberAuth(RegisterRequest registerRequest);
    public void deleteMemberAuth(String username);
}
