package io.moun.api.member.service;

import io.moun.api.member.controller.dto.MemberResponse;
import io.moun.api.member.controller.dto.MemberUpdateRequest;
import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.member.domain.Member;

import java.util.List;

public interface MemberApplicationService {
    MemberResponse register(RegisterRequest registerRequest);

    List<MemberResponse> findAllWithPositions();

    void delete(String username);

    MemberResponse findWithPositionsById(Long id);

    MemberResponse update(MemberUpdateRequest updateRequest, Long id);

    MemberResponse findById(Long id);
}
