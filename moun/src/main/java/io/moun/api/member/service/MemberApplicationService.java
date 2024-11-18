package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;

public interface MemberApplicationService {
    public MemberResponse register(RegisterRequest registerRequest);
    public void delete(String username);
    public MemberResponse findWithPositionsById(Long id);
    public MemberResponse findById(Long id);
    public MemberResponse update(MemberUpdateRequest updateRequest,Long id);
}
