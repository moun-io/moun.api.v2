package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;

public interface MemberApplicationService {
    MemberResponse register(RegisterRequest registerRequest);

    void delete(String username);

    MemberResponse findWithPositionsById(Long id);

    MemberResponse update(MemberUpdateRequest updateRequest, Long id);

    MemberResponse findById(Long id);
}
